import Patient from "../models/patient.js";
import Account from "../models/account.js";

// Update patient information
export const updatePatient = async (req, res) => {
  try {
    const patientId = req.params.PatientID;
    const updatedData = req.body;
    const patient = await Patient.findByIdAndUpdate(patientId, updatedData, {
      new: true,
    });
    if (!patient) {
      return res.status(404).json({ error: "Patient not found" });
    }
    return res.status(200).json(patient);
  } catch (error) {
    console.error(error);
    return res.status(500).json({ message: "Internal server error" });
  }
};

// Delete patient information
export const deletePatient = async (req, res) => {
  try {
    const patientId = req.params.PatientID;
    const patient = await Patient.findByIdAndDelete(patientId);
    if (!patient) {
      return res.status(404).json({ error: "Patient not found" });
    }
    return res.status(200).json({ message: "Patient deleted successfully" });
  } catch (error) {
    console.error(error);
    return res.status(500).json({ message: "Internal server error" });
  }
};

// View patient information
export const viewPatient = async (req, res) => {
  try {
    const patientId = req.params.PatientID;
    const patient = await Patient.findById(patientId);
    if (!patient) {
      return res.status(404).json({ error: "Patient not found" });
    }
    return res.status(200).json(patient);
  } catch (error) {
    console.error(error);
    return res.status(500).json({ message: "Internal server error" });
  }
};

export const getPatientWhenIDandPhonumberisTrue = async (req, res) => {
  try {
    const PatientID = req.params.PatientID;
    const Phonenumber = req.params.Phonenumber;
  } catch (error) {
    console.log(error);
  }
};

export const getAllPatientByPhoneNumber = async (req, res, next) => {
  try {
    const accountPhoneNumber = req.params.PhoneNumber;
    console.log("Account PhoneNumber: ", accountPhoneNumber);

    // Find the account with the provided phone number
    const account = await Account.findOne({ PhoneNumber: accountPhoneNumber });

    if (account) {
      // If account exists, fetch patients associated with the account's phone number
      const patients = await Patient.find({ PhoneNumber: account.PhoneNumber });
      console.log("Patients: ", patients);

      if (patients.length > 0) {
        return res.status(200).json(patients);
      } else {
        return res
          .status(404)
          .json({ message: "No patients found with this phone number" });
      }
    } else {
      // If no account found with the provided phone number
      return res
        .status(404)
        .json({ message: "No account found with this phone number" });
    }
  } catch (error) {
    console.error(error);
    return res.status(500).json({ message: "Internal server error" });
  }
};
