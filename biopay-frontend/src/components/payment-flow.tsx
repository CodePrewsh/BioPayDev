"use client"

import { useState } from "react"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { Badge } from "@/components/ui/badge"
import { Shield, CreditCard, Lock, AlertTriangle } from "lucide-react"
import { toast } from "sonner"

interface PaymentFlowProps {
  fraudScore: number
  isLive: boolean
  transactionId?: string
  onComplete: () => void
}

export default function PaymentFlow({ fraudScore, isLive, transactionId, onComplete }: PaymentFlowProps) {
  const [isProcessing, setIsProcessing] = useState(false)

  const riskLevel = fraudScore < 0.3 ? "low" : fraudScore < 0.7 ? "medium" : "high"
  const amount = 149.99 // This could come from props or backend

  const handlePayment = async () => {
    if (!isLive) {
      toast.error("Liveness check failed. Cannot proceed with payment.")
      return
    }

    setIsProcessing(true)

    try {
      // Call backend to charge YOCO token
      const response = await fetch("/api/payment/charge", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          transactionId,
          amount,
          fraudScore,
          biometricVerified: isLive
        })
      })

      if (!response.ok) throw new Error("Payment failed")

      toast.success("Payment authorized and processed successfully!")
      onComplete()
    } catch (err) {
      console.error(err)
      toast.error("Payment could not be processed. Please try again.")
    } finally {
      setIsProcessing(false)
    }
  }

  const getRiskColor = () => {
    switch (riskLevel) {
      case "low":
        return "text-green-600 bg-green-100"
      case "medium":
        return "text-yellow-600 bg-yellow-100"
      case "high":
        return "text-red-600 bg-red-100"
      default:
        return "text-gray-600 bg-gray-100"
    }
  }

  return (
    <div className="space-y-6">
      <div className="text-center">
        <h3 className="text-xl font-semibold mb-2">Payment Authorization</h3>
        <p className="text-gray-600">Biometric and fraud checks complete</p>
      </div>

      <Card>
        <CardHeader>
          <CardTitle className="flex items-center justify-between">
            <span className="flex items-center gap-2">
              <CreditCard className="w-5 h-5" />
              Transaction Details
            </span>
            <Badge className={getRiskColor()}>{riskLevel.toUpperCase()} RISK</Badge>
          </CardTitle>
        </CardHeader>

        <CardContent className="space-y-4">
          <div className="flex justify-between items-center text-lg">
            <span>Amount:</span>
            <span className="font-bold">R {amount.toFixed(2)}</span>
          </div>

          <div className="bg-gray-50 p-4 rounded-lg space-y-2">
            <div className="flex justify-between text-sm">
              <span>Fraud Score:</span>
              <span className={fraudScore < 0.3 ? "text-green-600" : fraudScore < 0.7 ? "text-yellow-600" : "text-red-600"}>
                {Math.round(fraudScore * 100)}%
              </span>
            </div>
            <div className="flex justify-between text-sm">
              <span>Liveness Verified:</span>
              <span className={isLive ? "text-green-600" : "text-red-600"}>
                {isLive ? "Yes" : "No"}
              </span>
            </div>
            <div className="flex justify-between text-sm">
              <span>Payment Method:</span>
              <span className="text-green-600">Tokenized Card (via YOCO)</span>
            </div>
          </div>

          {riskLevel !== "low" && (
            <div className="flex items-center gap-2 text-yellow-700 bg-yellow-50 p-3 rounded-lg">
              <AlertTriangle className="w-4 h-4" />
              <span>Higher risk detected. Backend may apply extra checks.</span>
            </div>
          )}

          <div className="flex items-center gap-2 text-sm text-green-600 bg-green-50 p-3 rounded-lg">
            <Shield className="w-4 h-4" />
            <span>Transaction secured with YOCO and AI-powered fraud detection</span>
          </div>

          <Button
            onClick={handlePayment}
            disabled={isProcessing || !isLive}
            className="w-full"
            size="lg"
          >
            {isProcessing ? (
              <>
                <Lock className="w-4 h-4 mr-2 animate-spin" />
                Processing Payment...
              </>
            ) : (
              <>
                <Lock className="w-4 h-4 mr-2" />
                Authorize Payment
              </>
            )}
          </Button>
        </CardContent>
      </Card>
    </div>
  )
}
