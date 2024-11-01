import express from "express";
import routes from "./src/routes/index.js";
import cors from "cors";
import morgan from "morgan";
import swaggerUi from "swagger-ui-express";
import swaggerFile from "./swagger-output.json" assert {type: "json"};

const app = express();
const porta = process.env.PORT || 3000;

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

app.use('/docs', swaggerUi.serve, swaggerUi.setup(swaggerFile))

app.listen(porta, () => {
  console.log(`Server rodando em http://localhost:${porta}`);
});
