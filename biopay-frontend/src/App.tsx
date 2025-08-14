"use client"

import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom"
import LandingPage from "@/pages/LandingPage"
import LoginPage from "@/pages/LoginPage"
import RegisterPage from "@/pages/RegisterPage"
import DashboardPage from "@/pages/DashboardPage"
import PaymentPage from "@/pages/PaymentPage"

const isAuthenticated = () => localStorage.getItem("auth") === "true"

export default function App() {
  return (
    <BrowserRouter>
      {/* Main wrapper centers content horizontally */}
      <div className="min-h-screen bg-gray-100 flex flex-col items-center">
        {/* Optional inner container limits width */}
        <div className="w-full max-w-4xl p-4">
          <Routes>
            <Route path="/" element={<LandingPage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route
              path="/dashboard"
              element={isAuthenticated() ? <DashboardPage /> : <Navigate to="/login" />}
            />
            <Route
              path="/payment"
              element={isAuthenticated() ? <PaymentPage /> : <Navigate to="/login" />}
            />
            <Route path="*" element={<Navigate to="/" />} />
          </Routes>
        </div>
      </div>
    </BrowserRouter>
  )
}
