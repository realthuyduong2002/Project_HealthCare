import express from "express";
const router = express.Router();
import {
  login,
  register,
  getPhonenumber,
  changePassword,
} from "../controllers/Authentication.js";

router.get("/getPhonenumber/:phonenumber", getPhonenumber);
router.post("/login", login);
router.post("/register", register);
router.put("/changePassword/:phonenumber", changePassword);

export default router;
