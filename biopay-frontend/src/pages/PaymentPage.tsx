"use client"

import { useForm } from "react-hook-form"
import BiometricPaymentDemo from "@/components/BiometricPaymentDemo"
import { Form, FormItem, FormLabel, FormControl, FormDescription } from "@/components/ui/form"
import { Button } from "@/components/ui/button"
import { useNavigate } from "react-router-dom"

export default function PaymentPage() {
  const form = useForm()
  const navigate = useNavigate()

  const handleLogout = () => {
    // Clear local storage / simulate logout
    localStorage.removeItem("auth")

    // TODO: Call backend logout endpoint if applicable
    // e.g., await fetch("/api/logout", { method: "POST" })

    navigate("/login")
  }

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50 p-4">
      <div className="outer-form-wrapper w-full max-w-5xl">
        <Form {...form}>
          <div className="inner-form-card w-full space-y-6">
            <h1 className="text-2xl font-bold text-center">Biometric Payment</h1>

            <FormItem>
              <FormLabel>Payment Flow</FormLabel>
              <FormControl asChild>
                <div>
                  <BiometricPaymentDemo />
                </div>
              </FormControl>
              <FormDescription>
                Complete your payment securely using your biometric data.
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
