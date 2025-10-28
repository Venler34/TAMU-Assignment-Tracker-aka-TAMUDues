"use client";
import { useState } from "react";
import {SignIn, Register} from "@/app/lib/account";
import { useRouter } from "next/navigation";

interface RegisterDropdownProps {
  onBackToSignIn: () => void;
}

export default function RegisterDropdown({ onBackToSignIn }: RegisterDropdownProps) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const router = useRouter();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setMessage("");

    if(await Register(username, password)) {
      if(await SignIn(username, password)) {
        router.push("/dashboard");
      } else {
        setMessage("An error occurred. Please try again.");
      }
    } else {
      setMessage("An error occurred. Please try again.");
    }
  };

  return (
    <form
      onSubmit={handleSubmit}
      className="bg-green-500 w-64 p-6 rounded shadow-lg flex flex-col space-y-4 text-white"
    >
      <h2 className="text-center text-lg font-semibold">Register</h2>
      <p className="text-sm text-center">
        Already have an account?{" "}
        <button
          type="button"
          onClick={onBackToSignIn}
          className="underline hover:text-green-200"
        >
          Sign In
        </button>
      </p>

      <input
        type="text"
        placeholder="Username"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
        className="bg-green-700 p-2 rounded text-white placeholder-white"
      />
      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        className="bg-green-700 p-2 rounded text-white placeholder-white"
      />
      <button
        type="submit"
        className="bg-green-700 py-2 rounded hover:bg-green-800 transition"
      >
        Register
      </button>

      {message && <p className="text-sm text-center">{message}</p>}
    </form>
  );
}
