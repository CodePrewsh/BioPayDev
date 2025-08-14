"use client"

import { useState } from "react"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { Badge } from "@/components/ui/badge"
import { Fingerprint, Shield, CheckCircle, Camera } from "lucide-react"
import BiometricCapture from "@/components/biometric-capture"
import FraudDetection from "@/components/fraud-detection"
import PaymentFlow from "@/components/payment-flow"

export default function BiometricPaymentDemo() {
  const [currentStep, setCurrentStep] = useState<"capture" | "analysis" | "payment" | "complete">("capture")
  const [biometricData, setBiometricData] = useState<any>(null)
  const [fraudScore, setFraudScore] = useState<number>(0)
  const [isLive, setIsLive] = useState<boolean>(false)
  const [transactionId, setTransactionId] = useState<string | null>(null)

  const handleBiometricCapture = async (data: any) => {
    // This would be your real API call to the backend for fingerprint processing
    setBiometricData(data)
    setCurrentStep("analysis")
  }

  const handleFraudAnalysis = async (score: number, liveness: boolean) => {
    // Here you'd hit your fraud detection API
    setFraudScore(score)
    setIsLive(liveness)

    // Generate a transaction ID (mock for now)
    setTransactionId(`TX-${Date.now()}`)
    setCurrentStep("payment")
  }

  const handlePaymentComplete = async () => {
    // Here you’d integrate YOCO payment finalization
    setCurrentStep("complete")
  }

  const resetTransaction = () => {
    setCurrentStep("capture")
    setBiometricData(null)
    setFraudScore(0)
    setIsLive(false)
    setTransactionId(null)
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 p-4">
      <div className="max-w-4xl mx-auto">
        {/* Header */}
        <div className="text-center mb-8">
          <h1 className="text-4xl font-bold text-gray-900 mb-2">BioPay</h1>
          <p className="text-xl text-gray-600">AI-Powered Biometric Payment with YOCO Integration</p>
        </div>

        {/* Feature Cards */}
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6 mb-8">
          <Card className="border-2 border-blue-200">
            <CardHeader className="text-center">
              <Fingerprint className="w-12 h-12 mx-auto text-blue-600 mb-2" />
              <CardTitle>Biometric Auth</CardTitle>
              <CardDescription>Fingerprint + Liveness Detection</CardDescription>
            </CardHeader>
          </Card>

          <Card className="border-2 border-green-200">
            <CardHeader className="text-center">
              <Shield className="w-12 h-12 mx-auto text-green-600 mb-2" />
              <CardTitle>AI Fraud Detection</CardTitle>
              <CardDescription>Real-time Risk Analysis</CardDescription>
            </CardHeader>
          </Card>

          <Card className="border-2 border-purple-200">
            <CardHeader className="text-center">
              <Camera className="w-12 h-12 mx-auto text-purple-600 mb-2" />
              <CardTitle>YOCO Payment</CardTitle>
              <CardDescription>Secure Transaction Processing</CardDescription>
            </CardHeader>
          </Card>
        </div>

        {/* Payment Flow */}
        <Card className="mb-6">
          <CardHeader>
            <CardTitle className="flex items-center gap-2">
              Payment Flow
              <Badge variant={currentStep === "complete" ? "default" : "secondary"}>
                {currentStep.charAt(0).toUpperCase() + currentStep.slice(1)}
              </Badge>
            </CardTitle>
          </CardHeader>
          <CardContent>
            {currentStep === "capture" && <BiometricCapture onCapture={handleBiometricCapture} />}
            {currentStep === "analysis" && (
              <FraudDetection biometricData={biometricData} onAnalysisComplete={handleFraudAnalysis} />
            )}
            {currentStep === "payment" && (
              <PaymentFlow
                fraudScore={fraudScore}
                isLive={isLive}
                transactionId={transactionId ?? undefined}
                onComplete={handlePaymentComplete}
              />
            )}
            {currentStep === "complete" && (
              <div className="text-center py-8">
                <CheckCircle className="w-16 h-16 mx-auto text-green-600 mb-4" />
                <h3 className="text-2xl font-bold text-green-600 mb-2">Payment Successful!</h3>
                <p className="text-gray-600">
                  Transaction {transactionId} completed securely with biometric verification
                </p>
                <Button onClick={resetTransaction} className="mt-4">
                  New Transaction
                </Button>
              </div>
            )}
          </CardContent>
        </Card>
      </div>
    </div>
  )
}
