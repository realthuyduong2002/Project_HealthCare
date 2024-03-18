import Medicine from "../models/medicine.js";
import Counter from "../models/counter.js";

export const getAllMedicines = async (req, res, next) => {
    try {
        const medicines = await Medicine.find();
        res.status(200).json(medicines);
    } catch (err) {
        console.error("Error in getAllMedicines:", err);
        res.status(500).json({ error: "Internal Server Error" });
    }
};

export const getMedicineByID = async (req, res, next) => {
    try {
        const medicine = await Medicine.findById(req.params.id);
        if (!medicine) {
            return res.status(404).json({ error: "Medicine not found" });
        }
        res.status(200).json(medicine);
    } catch (error) {
        console.error("Error in getMedicineByID:", error);
        res.status(500).json({ error: "Internal Server Error" });
    }
};

export const addMedicine = async (req, res, next) => {
    try {
        const counter = await Counter.findOneAndUpdate(
            { _id: "medicineId" },
            { $inc: { sequence_value: 1 } },
            { new: true, upsert: true }
        );
        const newMedicine = new Medicine({
            _id: counter.sequence_value,
            ...req.body,
        });
        const savedMedicine = await newMedicine.save();
        res.status(200).json(savedMedicine);
    } catch (error) {
        console.error("Error in addMedicine:", error);
        res.status(500).json({ error: "Internal Server Error" });
    }
};

export const updateMedicine = async (req, res, next) => {
    try {
        const medicineId = req.params.id;
        const updateData = req.body;
        const updatedMedicine = await Medicine.findByIdAndUpdate(medicineId, updateData, {
            new: true,
        });
        if (!updatedMedicine) {
            return res.status(404).json({ error: "Medicine not found" });
        }
        res.status(200).json(updatedMedicine);
    } catch (error) {
        console.error("Error in updateMedicine:", error);
        res.status(500).json({ error: "Internal Server Error" });
    }
};

export const deleteMedicine = async (req, res, next) => {
    try {
        const medicineId = req.params.id;
        const deletedMedicine = await Medicine.findByIdAndDelete(medicineId);
        if (!deletedMedicine) {
            return res.status(404).json({ error: "Medicine not found" });
        }
        res.status(200).json({ message: "Medicine deleted successfully" });
    } catch (error) {
        console.error("Error in deleteMedicine:", error);
        res.status(500).json({ error: "Internal Server Error" });
    }
};

export const searchMedicine = async (req, res, next) => {
    try {
        const keyword = req.params.keyword;
        const searchOptions = {
            $text: { $search: keyword },
        };
        const medicines = await Medicine.find(searchOptions);
        res.status(200).json(medicines);
    } catch (error) {
        console.error("Error in searchMedicine:", error);
        res.status(500).json({ error: "Internal Server Error" });
    }
};