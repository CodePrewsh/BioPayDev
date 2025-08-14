"use client"

import { useForm } from "react-hook-form"
import { Link, useNavigate } from "react-router-dom"
import { Button } from "@/components/ui/button"
import { Form, FormItem, FormLabel, FormControl, FormDescription } from "@/components/ui/form"

export default function DashboardPage() {
  const form = useForm()
  const navigate = useNavigate()

  const handleLogout = () => {
    // Simulate logout
    localStorage.removeItem("auth")

    // TODO: Add backend logout call if needed
    // e.g., await fetch("/api/logout", { method: "POST" })

    navigate("/login")
  }

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50 p-4">
      <div className="outer-form-wrapper w-full max-w-md">
        <Form {...form}>
          <div className="inner-form-card w-full space-y-6 text-center">
            <h1 className="text-2xl font-bold text-gray-900">Dashboard</h1>
            <p className="text-gray-600">
              Welcome back! What would you like to do today?
            </p>

            <FormItem>
              <FormLabel>Start Payment</FormLabel>
              <FormControl asChild>
                <Link to="/payment">
                  <Button size="lg" className="w-full">
                    Start Biometric Payment
                  </Button>
                </Link>
              </FormControl>
              <FormDescription>
                Click above to initiate a secure biometric payment.
              </FormDescription>
            </FormItem>

            {/* Logout Button */}
            <div className="pt-4">
              <Button type="button" onClick={handleLogout} className="w-full bg-red-500 hover:bg-red-600">
                Logout
              </Button>
            </div>
          </div>
        </Form>
      </div>
    </div>
  )
}
