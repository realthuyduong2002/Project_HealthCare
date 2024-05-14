import Account from "../models/account.js";
import bcrypt from "bcryptjs";
import jwt from "jsonwebtoken";

export const register = async (req, res, next) => {
  try {
    const { PhoneNumber, Password, Role } = req.body;

    if (!PhoneNumber || !Password) {
      return res.status(400).json("PhoneNumber and Password are required");
    }

    const salt = bcrypt.genSaltSync(10);
    const hash = bcrypt.hashSync(Password, salt);

    const existingAccount = await Account.findOne({ PhoneNumber });

    if (existingAccount) {
      return res.status(401).json("PhoneNumber already exists");
    }

    const newAccount = new Account({
      PhoneNumber,
      Password: hash,
      Role,
    });

    await newAccount.save();

    res.status(201).send("Account has been created");
  } catch (error) {
    next(error);
  }
};

export const login = async (req, res, next) => {
  try {
    const { PhoneNumber, Password: loginPassword } = req.body;

    if (!PhoneNumber || !loginPassword) {
      return res.status(400).json("PhoneNumber and Password are required");
    }

    const account = await Account.findOne({ PhoneNumber });

    if (!account) {
      return res.status(404).json("Account not found");
    }

    const isCorrectPassword = await bcrypt.compare(
      loginPassword,
      account.Password
    );

    if (!isCorrectPassword) {
      return res.status(400).json("Wrong password or phonenumber!");
    }

    const token = jwt.sign(
      { id: account._id, Role: account.Role },
      process.env.JWT
    );

    // Log the role of the user
    const role = account.Role === true ? "Admin" : "User";
    console.log(`Role: ${role}`);

    const { Password, Role, ...otherDetails } = account._doc;

    res
      .cookie("access_token", token, { httpOnly: true })
      .status(200)
      .json({
        details: { ...otherDetails },
        role,
      });
  } catch (error) {
    next(error);
  }
};
