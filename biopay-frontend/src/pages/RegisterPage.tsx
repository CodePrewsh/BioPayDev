"use client"

import { useState } from "react"
import { useNavigate, Link } from "react-router-dom"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { toast } from "sonner"
import BiometricCapture from "@/components/biometric-capture"

export default function RegisterPage() {
  const navigate = useNavigate()
  const [name, setName] = useState("")
  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")
  const [biometricData, setBiometricData] = useState<string | null>(null)

  const handleBiometricCaptured = (data: string) => {
    setBiometricData(data)
    toast.success("Biometric captured successfully!")
  }

  const handleRegister = (e: React.FormEvent) => {
    e.preventDefault()

    if (!name || !email || !password) {
      toast.error("Please fill in all fields")
      return
    }
    if (!biometricData) {
      toast.error("Please capture your biometric data")
      return
    }

    // Mock API call
    const payload = { name, email, password, biometric: biometricData }
    console.log("Registration payload:", payload)
    toast.success("Registration successful!")
    localStorage.setItem("auth", "true")
    navigate("/dashboard")
  }

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50 p-4">
      <Card className="w-full max-w-md">
        <CardHeader>
          <div className="flex justify-between items-center">
            <Button
              type="button"
              onClick={() => navigate("/")}
              className="bg-gray-200 text-gray-700 hover:bg-gray-300 px-3 py-1 rounded"
            >
              Home
            </Button>
            <CardTitle className="text-2xl font-bold text-center flex-1">
              Register
            </CardTitle>
          </div>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleRegister} className="space-y-4">
            <Input
              placeholder="Full name"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
            <Input
              type="email"
              placeholder="you@example.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
            <Input
              type="password"
              placeholder="••••••••"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />

            {/* Biometric Capture */}
            <div className="border rounded-md p-3">
              <BiometricCapture onCapture={handleBiometricCaptured} />
              {biometricData && (
                <p className="text-green-600 text-sm mt-2">Biometric ready ✅</p>
              )}
            </div>

            <Button type="submit" className="w-full">
              Create Account
            </Button>
          </form>

          <p className="text-sm text-center text-gray-600 mt-4">
            Already have an account?{" "}
            <Link to="/login" className="text-blue-600 hover:underline">
              Login
            </Link>
          </p>
        </CardContent>
      </Card>
    </div>
  )
}
