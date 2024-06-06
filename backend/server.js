import express from "express";
import routes from "./src/routes/index.js";
import cors from "cors";
import morgan from "morgan";

const app = express();
const porta = 3000;

app.use(
  cors({
    origin: "*",
    methods: ["GET", "POST", "PUT", "DELETE", "PATCH"],
    allowedHeaders: [
      "Content-Type",
      "Authorization",
      "Origin",
      "X-Requested-With",
      "Accept",
    ],
  })
);
app.use(express.json());
app.use(morgan("dev"));
app.use("/api", routes);

app.listen(porta, () => {
  console.log(`Server rodando em http://localhost:${porta}`);
});
