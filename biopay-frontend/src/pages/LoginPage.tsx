"use client";

import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { toast } from "sonner";
import { Link } from "react-router-dom";
import BiometricCapture from "@/components/biometric-capture"; // <-- Your biometric component

export default function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [biometricData, setBiometricData] = useState<string | null>(null);

  const handlePasswordLogin = (e: React.FormEvent) => {
    e.preventDefault();

    if (!email || !password) {
      toast.error("Please fill in all fields");
      return;
    }

    // Placeholder for API call
    console.log("Password login payload:", { email, password });
    toast.success("Logged in successfully!");
  };

  const handleBiometricCaptured = (data: string) => {
    setBiometricData(data);
    console.log("Biometric login payload:", { biometric: data });
    toast.success("Biometric login successful!");
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50 p-4">
      <Card className="w-full max-w-md">
        <CardHeader>
          <CardTitle className="text-2xl font-bold text-center">Login</CardTitle>
        </CardHeader>
        <CardContent>
          {/* Password Login Form */}
          <form onSubmit={handlePasswordLogin} className="space-y-4 mb-6">
            <Input type="email" placeholder="you@example.com" value={email} onChange={(e) => setEmail(e.target.value)} />
            <Input type="password" placeholder="••••••••" value={password} onChange={(e) => setPassword(e.target.value)} />
            <Button type="submit" className="w-full">Login</Button>
          </form>

          <div className="relative flex items-center justify-center mb-4">
            <span className="px-3 bg-white text-gray-500 text-sm">OR</span>
          </div>

          {/* Biometric Login */}
          <div className="border rounded-md p-3">
            <BiometricCapture onCapture={handleBiometricCaptured} />
            {biometricData && <p className="text-green-600 text-sm mt-2">Biometric verified ✅</p>}
          </div>

          <p className="text-sm text-center text-gray-600 mt-4">
            Don't have an account?{" "}
            <Link to="/register" className="text-blue-600 hover:underline">Register</Link>
          </p>
        </CardContent>
      </Card>
    </div>
  );
}
