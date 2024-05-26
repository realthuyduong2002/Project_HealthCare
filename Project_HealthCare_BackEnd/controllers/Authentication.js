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
      return res.status(400).json("Wrong password!");
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
        id: account._id,
        details: { ...otherDetails },
        role,
      });
  } catch (error) {
    return res.status(500).json("Internal server error", error);
  }
};

export const changePassword = async (req, res) => {
  try {
    const phonenumber = req.params.PhoneNumber;
    const updatedData = req.body;

    // Retrieve the account
    const account = await Account.findOne({ phonenumber });

    if (!account) {
      return res.status(404).json({ error: "Account not found" });
    }

    // Ensure that you have a valid secret key in your environment variables
    if (!process.env.JWT) {
      return res.status(500).json({ error: "JWT secret key is missing" });
    }

    // Hash the updated password
    const salt = bcrypt.genSaltSync(10);
    const hash = bcrypt.hashSync(updatedData.Password, salt);
    updatedData.Password = hash;

    // Update the password
    const updatedAccount = await Account.findOneAndUpdate(
      { phonenumber: phonenumber },
      updatedData,
      { new: true }
    );

    if (!updatedAccount) {
      return res.status(404).json({ error: "Failed to update password" });
    }

    // Sign a new JWT token using the secret key
    const token = jwt.sign(
      { id: updatedAccount._id, Role: updatedAccount.Role },
      process.env.JWT // Use the JWT secret key from environment variables
    );

    // Extract details to exclude password and role
    const { Password, Role, ...otherDetails } = updatedAccount._doc;

    // Send response with updated details and role, along with the new token
    res
      .cookie("access_token", token, { httpOnly: true })
      .status(200)
      .json({
        message: "Update success",
        details: { ...otherDetails },
        role: updatedAccount.Role === true ? "Admin" : "User", // Use updatedAccount.Role to determine role
      });
  } catch (error) {
    console.log(error);
    return res.status(500).json({ error: "Internal server error" });
  }
};

export const getPhonenumber = async (req, res) => {
  try {
    const { phonenumber } = req.params;
    const existingPhonenumber = await Account.findOne({
      PhoneNumber: phonenumber,
    });

    if (!existingPhonenumber) {
      return res.status(404).json({ error: "Phone number not found" });
    }
    return res.status(200).json(existingPhonenumber);
  } catch (error) {
    console.log(error);
    return res.status(500).json({ error: "Internal server error" });
  }
};
