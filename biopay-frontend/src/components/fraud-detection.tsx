"use client"

import { useState, useEffect } from "react"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Progress } from "@/components/ui/progress"
import { Badge } from "@/components/ui/badge"
import { Shield, CheckCircle, AlertTriangle } from "lucide-react"

interface FraudDetectionProps {
  biometricData: any
  onAnalysisComplete: (fraudScore: number, isLive: boolean) => void
}

export default function FraudDetection({ biometricData, onAnalysisComplete }: FraudDetectionProps) {
  const [progress, setProgress] = useState(0)
  const [isComplete, setIsComplete] = useState(false)
  const [fraudScore, setFraudScore] = useState(0)
  const [isLive, setIsLive] = useState(false)

  useEffect(() => {
    let value = 0
    const interval = setInterval(() => {
      value += 5
      setProgress(value)
      if (value >= 100) {
        clearInterval(interval)

        // Simple placeholder scoring logic
        const mockFraudScore = Math.random() * 0.6 // 0 to 0.6 (low to medium)
        const mockIsLive = Math.random() > 0.1 // 90% chance of true

        setFraudScore(mockFraudScore)
        setIsLive(mockIsLive)
        setIsComplete(true)

        // Pass results back to parent
        onAnalysisComplete(mockFraudScore, mockIsLive)
      }
    }, 150)

    return () => clearInterval(interval)
  }, [biometricData, onAnalysisComplete])

  const riskLevel = fraudScore < 0.3 ? "low" : fraudScore < 0.7 ? "medium" : "high"
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
    <div className="space-y-4">
      <div className="text-center">
        <h3 className="text-lg font-semibold">Running Fraud Detection...</h3>
        <p className="text-sm text-gray-500">Analyzing biometric data for risk factors</p>
      </div>

      <Card>
        <CardHeader className="flex items-center gap-2">
          <Shield className="w-5 h-5 text-blue-600" />
          <CardTitle>Analysis Progress</CardTitle>
        </CardHeader>
        <CardContent>
          <Progress value={progress} className="h-2 mb-4" />

          {isComplete && (
            <div className="space-y-3">
              <div className="flex items-center justify-between">
                <span className="font-medium">Risk Level</span>
                <Badge className={getRiskColor()}>{riskLevel.toUpperCase()}</Badge>
              </div>
              <div className="flex items-center justify-between">
                <span className="font-medium">Liveness Check</span>
                {isLive ? (
                  <CheckCircle className="w-5 h-5 text-green-600" />
                ) : (
                  <AlertTriangle className="w-5 h-5 text-red-600" />
                )}
              </div>
            </div>
          )}
        </CardContent>
      </Card>
    </div>
  )
}
