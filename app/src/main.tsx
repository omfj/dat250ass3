import "./index.css";
import { StrictMode, useState } from "react";
import { createRoot } from "react-dom/client";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import {
  Route,
  RouterProvider,
  createBrowserRouter,
  createRoutesFromElements,
} from "react-router-dom";
import { Root } from "./routes/root";
import { DefaultLayout } from "./layouts/default";
import { PollCreate } from "./routes/polls.create";
import { Poll } from "./routes/polls.$id";
import { Login } from "./routes/login";
import { Register } from "./routes/register";

const router = createBrowserRouter(
  createRoutesFromElements(
    <Route path="/" element={<DefaultLayout />}>
      <Route path="/" index element={<Root />}></Route>
      <Route path="/login" element={<Login />}></Route>
      <Route path="/register" element={<Register />}></Route>
      <Route path="/polls/create" element={<PollCreate />}></Route>
      <Route path="/polls/:id" element={<Poll />}></Route>
    </Route>
  )
);

// eslint-disable-next-line react-refresh/only-export-components
const App = () => {
  const [queryClient] = useState(() => new QueryClient());

  return (
    <QueryClientProvider client={queryClient}>
      <RouterProvider router={router} />
    </QueryClientProvider>
  );
};

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <App />
  </StrictMode>
);
