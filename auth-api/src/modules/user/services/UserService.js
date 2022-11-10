import bcrypt from "bcrypt";
import jwt from "jsonwebtoken";

import {
  BAD_REQUEST,
  FORBIDDEN,
  INTERNAL_SERVER_ERROR,
  SUCCESS,
  UNAUTHORIZED,
} from "../../../config/constants/httpStatus.js";
import { apiSecret } from "../../../config/constants/secrets.js";

import UserException from "../exceptions/UserException.js";
import UserRepository from "../repositories/UserRepository.js";

class UserService {
  validateRequestData(email) {
    if (!email)
      throw new UserException(BAD_REQUEST, "Email user was not informed.");
  }

  validateUserNotFound(user) {
    if (!user) throw new UserException(BAD_REQUEST, "User was not informed.");
  }

  validateAccessTokenData(email, password) {
    if (!email || !password)
      throw new UserException(
        UNAUTHORIZED,
        "Email and password must be informed."
      );
  }

  async validatePassword(password, hashPassword) {
    if (!(await bcrypt.compare(password, hashPassword)))
      throw new UserException(UNAUTHORIZED, "Password doesn't match.");
  }

  validateAuthUser(user, authUser) {
    if (!authUser || user.id !== authUser.id)
      throw new UserException(FORBIDDEN, "You cannot see this user data.");
  }

  async getAccessToken(req) {
    try {
      const { email, password } = req.body;
      this.validateAccessTokenData(email, password);

      const user = await UserRepository.findByEmail(email);
      this.validateUserNotFound(user);
      await this.validatePassword(password, user.password);

      const authUser = {
        id: user.id,
        name: user.name,
        email: user.email,
      };

      const accessToken = jwt.sign({ authUser }, apiSecret, {
        expiresIn: "1d",
      });

      return {
        status: SUCCESS,
        accessToken,
      };
    } catch (error) {
      return {
        status: error.status ?? INTERNAL_SERVER_ERROR,
        message: error.message,
      };
    }
  }

  async findByEmail(req) {
    try {
      const { email } = req.params;
      const { authUser } = req;
      this.validateRequestData(email);

      const user = await UserRepository.findByEmail(email);
      this.validateUserNotFound(user);
      this.validateAuthUser(user, authUser);

      return {
        status: SUCCESS,
        user: {
          id: user.id,
          name: user.name,
          email: user.email,
        },
      };
    } catch (error) {
      return {
        status: error.status ?? INTERNAL_SERVER_ERROR,
        message: error.message,
      };
    }
  }
}

export default new UserService();
