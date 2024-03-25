import express from "express";
import Patient from "../models/patient.js";
import Appointment from "../models/appointment.js";
import appointment from "../models/appointment.js";

export const getAllAppointment = async (req, res, next) => {
  try {
    const bills = await Appointment.find();
    if (!bills) {
      return res.status(404).json("Database empty");
    }
    return res.status(200).json(bills);
  } catch (error) {
    console.error(error);
    return res.status(500).json("Internal server error");
  }
};

//Get appointment by id
export const getAppointmentByID = async (req, res, next) => {
  try {
    const apppointmentId = req.params.id;
    const apppointment = await Appointment.findById(apppointmentId);

    if (!appointment) {
      return res.status(404).json("Appointment not found");
    }
    return res.status(200).json(apppointment);
  } catch (error) {
    console.error(error);
    return res.status(500).json("Internal server error");
  }
};
