import mongoose from "mongoose";
import Account from "../models/account.js";
import Doctor from "../models/doctor.js";
import bcrypt from "bcryptjs";
import jwt from "jsonwebtoken";

// Function to get doctor ID based on specific criteria
const getDoctorId = async (accountId) => {
  try {
    const doctor = await Doctor.findOne({ accountId: accountId });
    if (doctor) {
      return doctor._id;
    }
    return null;
  } catch (error) {
    throw error;
  }
};

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

    const savedAccount = await newAccount.save();
    console.log("accountId:", savedAccount._id);

    let accountId = savedAccount._id;
    let doctorId = null;

    if (Role) {
      accountId = accountId;

      const newDoctor = new Doctor({
        accountId: savedAccount._id,
        DoctorName: "Name",
        Gender: "Male",
        Email: "email@example.com",
        Phone: "0987654321",
        DateOfBirth: "01/01/2000",
        City: "City",
        Speciality: "Bone",
        WorkingDate: new Date(),
        WorkingTime: "08:00 AM",
      });

      const savedDoctor = await newDoctor.save();
      doctorId = savedDoctor._id;
    }

    res.status(201).json({
      message: "Account has been created",
      accountId: accountId,
      doctorId: doctorId,
    });
  } catch (error) {
    next(error);
  }
};

export const login = async (req, res, next) => {
  try {
    const { PhoneNumber, Password: loginPassword } = req.body;

    if (!PhoneNumber || !loginPassword) {
      return res.status(400).json({ message: "PhoneNumber and Password are required" });
    }

    const account = await Account.findOne({ PhoneNumber });
    console.log(account);

    if (!account) {
      return res.status(404).json({ message: "Account not found" });
    }

    const isCorrectPassword = await bcrypt.compare(loginPassword, account.Password);

    if (!isCorrectPassword) {
      return res.status(400).json({ message: "Wrong password!" });
    }

    const token = jwt.sign({ id: account._id, Role: account.Role }, process.env.JWT);

    // Log the role of the user
    const role = account.Role === true ? "Admin" : "User";
    console.log(`Role: ${role}`);

    const { Password, Role, ...otherDetails } = account._doc;

    const mDoctorIdString = otherDetails._id + ``;

    // Retrieve doctor ID if exists
    const doctorId = await getDoctorId(account._id);
    console.log(`Doctor ID: ${doctorId}`);

    res
      .cookie("access_token", token, { httpOnly: true })
      .status(200)
      .json({
        id: account._id + ``,
        details: { _id: mDoctorIdString },
        doctorId: doctorId,
        role,
      });
  } catch (error) {
    console.error("Error during login:", error);
    return res.status(500).json({ message: "Internal server error", error: error.message });
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
        role: updatedAccount.Role === true ? "Admin" : "User",
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