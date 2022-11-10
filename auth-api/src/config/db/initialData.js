import bcrypt from "bcrypt";

import User from "../../modules/user/models/User.js";

export const createInitialData = async () => {
  try {
    await User.sync({ force: true });

    const password = await bcrypt.hash("123456", 10);

    await User.create({
      name: "User Test",
      email: "test@gmail.com",
      password,
    });
  } catch (error) {
    console.error(error.message);
  }
};
