import express from "express";
import HealthRecord from "../models/healthRecord.js";
import healthRecord from "../models/healthRecord.js";
import Patient from "../models/patient.js";
import mongoose from "mongoose";

//GET ALL
export const getAllHealthRecord = async (req, res, next) => {
  try {
    const healthRecords = await HealthRecord.find();
    res.status(200).json(healthRecords);
  } catch (error) {
    console.error("Error in getHealthRecord: ", error);
    return res.status(500).json({ error: "Internal server error" });
  }
};

//GET BY ID
export const getHealthRecordById = async (req, res, next) => {
  try {
    const healthRecord = await HealthRecord.findById(req.params.id);
    if (!healthRecord) {
      return res.status(404).json({ error: "Health record not found" });
    }
    return res.status(200).json(healthRecord);
  } catch (error) {
    console.error("Error in getHealthRecordById: ", error);
    return res.status(500).json({ error: "Internal server error" });
  }
};

//Add new health record
export const addHealthRecord = async (req, res, next) => {
  try {
    const {
      PatientID,
      HeartBeat,
      CreationDate,
      Height,
      Weight,
      TypeOfPicture,
      BloodPressure,
      UnderlyingDisease,
    } = req.body;

    const existingPatient = await Patient.findById(PatientID);
    if (!existingPatient) {
      return res.status(404).json("Patient not found");
    }

    const newHealthRecord = new HealthRecord({
      PatientID: existingPatient._id,
      HeartBeat,
      CreationDate,
      Height,
      Weight,
      TypeOfPicture,
      BloodPressure,
      UnderlyingDisease,
    });
    const savedHealthRecord = await newHealthRecord.save();
    return res.status(200).json(savedHealthRecord);
  } catch (error) {
    console.error("Error in addHealthRecord: ", error);
    return res.status(500).json({ error: "Internal server error" });
  }
};

//UPDATE HEALTH RECORD
export const updateHealthRecord = async (req, res, next) => {
  try {
    const healthRecordId = req.params.id;
    const updateData = req.body;

    const updateHealthRecord = await HealthRecord.findByIdAndUpdate(
      healthRecordId,
      updateData,
      { new: true }
    );
    if (!updateHealthRecord) {
      return res
        .status(404)
        .json({ message: "Health record not found in order to update" });
    }
    return res.status(200).json(updateData);
  } catch (error) {
    console.error("Error in updateHealthRecord", error);
    return res.status(500).json({ error: "Internal server error" });
  }
};

//DELETE
export const deleteHealthRecord = async (req, res, next) => {
  try {
    const healthRecordId = req.params.id;
    const deleteHealthRecord = await healthRecord.findByIdAndDelete(
      healthRecordId
    );
    if (!deleteHealthRecord) {
      return res
        .status(404)
        .json("Health record doesn't exist in order to delete");
    }
    return res.status(200).json({ message: "Delete successful" });
  } catch (error) {
    console.error("Error in deleteHealthRecord: ", error);
    return res.status(500).json({ error: "Internal server error" });
  }
};
