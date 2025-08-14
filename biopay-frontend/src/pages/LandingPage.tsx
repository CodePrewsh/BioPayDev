"use client"

import { Link } from "react-router-dom"
import { Button } from "@/components/ui/button"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"

export default function LandingPage() {
  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50 p-4">
      <div className="outer-form-wrapper">
        <Card className="w-full text-center">
          <CardHeader>
            <CardTitle className="text-4xl font-bold text-gray-900">Welcome to BioPay</CardTitle>
          </CardHeader>
          <CardContent>
            <p className="text-lg text-gray-600 mb-6">
              AI-powered biometric payments with YOCO integration and real-time fraud detection.
            </p>
            <div className="flex gap-4 justify-center">
              <Link to="/login">
                <Button size="lg">Login</Button>
              </Link>
              <Link to="/register">
                <Button size="lg" variant="secondary">Register</Button>
              </Link>
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  )
}
