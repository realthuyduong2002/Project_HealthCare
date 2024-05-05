import Therapeutic from "../models/therapeutic.js";
import Counter from "../models/counter.js";

export const getAllTherapeutic = async (req, res, next) => {
  try {
    const therapeutics = await Therapeutic.find();
    res.status(200).json(therapeutics);
  } catch (err) {
    console.error("Error in getAllTherapeutic:", err);
    res.status(500).json({ error: "Internal Server Error" });
  }
};

export const getTherapeuticByID = async (req, res, next) => {
  try {
    const therapeutic = await Therapeutic.findById(req.params.id);
    if (!therapeutic) {
      return res.status(404).json({ error: "Therapeutic not found" });
    }
    res.status(200).json(therapeutic);
  } catch (error) {
    console.error("Error in getTherapeuticByID:", error);
    res.status(500).json({ error: "Internal Server Error" });
  }
};

export const addTherapeutic = async (req, res, next) => {
  try {
    const counter = await Counter.findOneAndUpdate(
      { _id: "therapeuticId" },
      { $inc: { sequence_value: 1 } },
      { new: true, upsert: true }
    );
    const newTherapeutic = new Therapeutic({
      _id: counter.sequence_value,
      ...req.body,
    });
    const savedTherapeutic = await newTherapeutic.save();
    res.status(200).json(savedTherapeutic);
  } catch (error) {
    console.error("Error in addTherapeutic:", error);
    res.status(500).json({ error: "Internal Server Error" });
  }
};

export const updateTherapeutic = async (req, res, next) => {
  try {
    const therapeuticId = req.params.id;
    const updateData = req.body;

    const updatedTherapeutic = await Therapeutic.findByIdAndUpdate(
      therapeuticId,
      updateData,
      { new: true }
    );

    if (!updatedTherapeutic) {
      return res.status(404).json({ error: "Therapeutic not found" });
    }

    res.status(200).json(updatedTherapeutic);
  } catch (error) {
    console.error("Error in updateTherapeutic:", error);
    res.status(500).json({ error: "Internal Server Error" });
  }
};
