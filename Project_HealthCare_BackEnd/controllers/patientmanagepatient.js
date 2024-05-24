import Patient from "../models/patient.js";

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
