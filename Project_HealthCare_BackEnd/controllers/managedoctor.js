import Doctor from "../models/doctor.js";

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
    return res.status(200).json(doctor);
  } catch (error) {
    console.error("Error in getDoctorByID:", error);
    return res.status(500).json({ error: "Internal Server Error" });
  }
};

export const addDoctor = async (req, res, next) => {
  try {
    const {
      DoctorName,
      Gender,
      Email,
      Phone,
      Speciality,
      WorkingDate,
      WorkingTime,
    } = req.body;

    const datePart = WorkingDate.split("/");
    const formatDate = new Date(`${datePart[2]}-${datePart[1]}-${datePart[0]}`);
    const newDoctor = new Doctor({
      DoctorName,
      Gender,
      Email,
      Phone,
      Speciality,
      WorkingDate: formatDate,
      WorkingTime,
    });

    const saveDoctor = await newDoctor.save();

    return res.status(200).json(saveDoctor);
  } catch (error) {
    console.log(error);
    return res.status(400).json("Internal server error", error);
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
