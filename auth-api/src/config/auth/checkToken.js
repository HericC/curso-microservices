import jwt from "jsonwebtoken";
import { promisify } from "util";

import {
  INTERNAL_SERVER_ERROR,
  UNAUTHORIZED,
} from "../constants/httpStatus.js";
import { apiSecret } from "../constants/secrets.js";

import AuthException from "./AuthException.js";

const bearer = "Bearer ";

export default async (req, res, next) => {
  try {
    const { authorization } = req.headers;
    if (!authorization)
      throw new AuthException(UNAUTHORIZED, "Access token was not informed.");

    let accessToken = authorization;
    if (accessToken.includes(bearer))
      accessToken = accessToken.replace(bearer, "");

    const decoded = await promisify(jwt.verify)(accessToken, apiSecret);
    req.authUser = decoded.authUser;

    return next();
  } catch (error) {
    const status = error.status ?? INTERNAL_SERVER_ERROR;
    return res.status(status).json({
      status,
      message: error.message,
    });
  }
};
