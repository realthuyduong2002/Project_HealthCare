import Patient from "../models/patient.js";

export const getAll = async (req, res, next) => {
  try {
    const patients = await Patient.find();
    res.status(200).json(patients);
  } catch (error) {
    next(error);
  }
};

export const getPatient = async (req, res, next) => {
  try {
    const patient = await Patient.findById(req.params.id);
    res.status(200).json(patient);
  } catch (error) {
    next(error);
  }
};

export const addpatient = async (req, res, next) => {
  const newPatient = new Patient(req.body);
  try {
    const savedPatient = await newPatient.save();
    res.status(200).json(savedPatient);
  } catch (error) {
    next(error);
  }
};
