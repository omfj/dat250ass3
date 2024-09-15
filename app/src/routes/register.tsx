import { useEffect, useState } from "react";
import { Button } from "../components/button";
import { Input } from "../components/input";
import { Label } from "../components/label";
import { apiClient } from "../lib/api/client";
import { useQueryClient } from "@tanstack/react-query";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../hooks/use-auth";

export const Register = () => {
  const queryClient = useQueryClient();
  const navigate = useNavigate();
  const { user, isLoading } = useAuth();
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [passwordRepeat, setPasswordRepeat] = useState("");

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    await apiClient.post("register", {
      json: {
        name,
        email,
        password,
      },
    });

    await queryClient.invalidateQueries();
  };

  useEffect(() => {
    if (!!user && !isLoading) {
      navigate("/");
    }
  }, [user, isLoading, navigate]);

  return (
    <main className="flex flex-col items-center justify-center py-16 px-4">
      <form
        onSubmit={handleSubmit}
        className="flex flex-col gap-4 w-full max-w-md rounded-xl border border-gray-200 p-4 shadow-lg"
      >
        <h1 className="text-2xl font-medium">Register account</h1>

        <div className="flex flex-col gap-1">
          <Label htmlFor="name">Name</Label>
          <Input
            type="name"
            id="name"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
        </div>

        <div className="flex flex-col gap-1">
          <Label htmlFor="email">Email</Label>
          <Input
            type="email"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </div>

        <div className="flex flex-col gap-1">
          <Label htmlFor="password">Password</Label>
          <Input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>

        <div className="flex flex-col gap-1">
          <Label htmlFor="password-repeat">Repeat password</Label>
          <Input
            type="password"
            id="password-repeat"
            value={passwordRepeat}
            onChange={(e) => setPasswordRepeat(e.target.value)}
          />

          {password !== passwordRepeat && (
            <p className="text-red-500">Passwords do not match</p>
          )}
        </div>

        <Button>Register</Button>
      </form>
    </main>
  );
};
