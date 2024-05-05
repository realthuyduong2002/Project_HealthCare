import Doctor from "../models/doctor.js";
import Counter from "../models/counter.js";

export const getAllDoctors = async (req, res, next) => {
    try {
        const doctors = await Doctor.find();
        res.status(200).json(doctors);
    } catch (err) {
        console.error("Error in getAllDoctors:", err);
        res.status(500).json({ error: "Internal Server Error" });
    }
};

export const getDoctorByID = async (req, res, next) => {
    try {
        const doctor = await Doctor.findById(req.params.id);
        if (!doctor) {
            return res.status(404).json({ error: "Doctor not found" });
        }
        res.status(200).json(doctor);
    } catch (error) {
        console.error("Error in getDoctorByID:", error);
        res.status(500).json({ error: "Internal Server Error" });
    }
};

export const addDoctor = async (req, res, next) => {
    try {
        const counter = await Counter.findOneAndUpdate(
            { _id: "doctorId" },
            { $inc: { sequence_value: 1 } },
            { new: true, upsert: true }
        );
        const newDoctor = new Doctor({
            _id: counter.sequence_value,
            ...req.body,
        });
        const savedDoctor = await newDoctor.save();
        res.status(200).json(savedDoctor);
    } catch (error) {
        console.error("Error in addDoctor:", error);
        res.status(500).json({ error: "Internal Server Error" });
    }
};

export const updateDoctor = async (req, res, next) => {
    try {
        const doctorId = req.params.id;
        const updateData = req.body;
        const updatedDoctor = await Doctor.findByIdAndUpdate(doctorId, updateData, {
            new: true,
        });
        if (!updatedDoctor) {
            return res.status(404).json({ error: "Doctor not found" });
        }
        res.status(200).json(updatedDoctor);
    } catch (error) {
        console.error("Error in updateDoctor:", error);
        res.status(500).json({ error: "Internal Server Error" });
    }
};

export const deleteDoctor = async (req, res, next) => {
    try {
        const doctorId = req.params.id;
        const deletedDoctor = await Doctor.findByIdAndDelete(doctorId);
        if (!deletedDoctor) {
            return res.status(404).json({ error: "Doctor not found" });
        }
        res.status(200).json({ message: "Doctor deleted successfully" });
    } catch (error) {
        console.error("Error in deleteDoctor:", error);
        res.status(500).json({ error: "Internal Server Error" });
    }
};

export const searchDoctor = async (req, res, next) => {
    try {
        const keyword = req.params.keyword;
        const searchOptions = {
            $text: { $search: keyword },
        };
        const doctors = await Doctor.find(searchOptions);
        res.status(200).json(doctors);
    } catch (error) {
        console.error("Error in searchDoctor:", error);
        res.status(500).json({ error: "Internal Server Error" });
    }
};