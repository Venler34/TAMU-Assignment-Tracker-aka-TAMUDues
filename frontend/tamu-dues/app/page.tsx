"use client";
import { useState } from "react";
import Navbar from "./components/Navbar";
import SignInDropdown from "./components/SignInDropdown";
import RegisterDropdown from "./components/RegisterDropdown";

export default function Home() {
  const [showDropdown, setShowDropdown] = useState(false);
  const [isRegister, setIsRegister] = useState(false);

  const toggleDropdown = () => setShowDropdown(!showDropdown);
  const handleRegisterClick = () => setIsRegister(true);
  const handleBackToSignIn = () => setIsRegister(false);

  return (
    <div className="bg-gray-900 min-h-screen flex flex-col items-center justify-start">
      <Navbar onSignInClick={toggleDropdown} />
      
      <div className="w-full flex-grow flex items-center justify-center bg-green-300 relative">
        <h1 className="text-6xl text-white font-semibold">TAMUDues</h1>

        {showDropdown && (
          <div className="absolute top-0 right-0">
            {isRegister ? (
              <RegisterDropdown onBackToSignIn={handleBackToSignIn} />
            ) : (
              <SignInDropdown onRegisterClick={handleRegisterClick} />
            )}
          </div>
        )}
      </div>
    </div>
  );
}
