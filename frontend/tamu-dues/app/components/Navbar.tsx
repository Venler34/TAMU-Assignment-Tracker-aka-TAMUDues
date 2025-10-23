"use client";

import {useState} from "react";
import SignIn from "./SignIn";

export default function Navbar() {
  const [signIn, setSignIn] = useState(false);

  return (
    <div>
        <nav className="navbar">
          <div>
              Home
          </div>
          <div>
              <button className="signIn" onClick={() => setSignIn(!signIn)}>Sign In</button>
          </div>
      </nav>
      <div className="signInBox">
        {
          signIn && <SignIn />
        }
      </div>
    </div>
  )
}