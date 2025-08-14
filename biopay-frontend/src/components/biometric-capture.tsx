"use client";

import React, { useState } from "react";
import { Button } from "@/components/ui/button";
import { toast } from "sonner";

interface BiometricCaptureProps {
  onCapture: (data: string) => void; // Callback when capture succeeds
}

export default function BiometricCapture({ onCapture }: BiometricCaptureProps) {
  const [isCapturing, setIsCapturing] = useState(false);
  const [capturedData, setCapturedData] = useState<string | null>(null);

  const handleCapture = async () => {
    setIsCapturing(true);
    toast.info("Capturing biometric...");

    try {
      // Simulate biometric scan delay
      await new Promise((resolve) => setTimeout(resolve, 2000));

      // Simulated biometric data (in real app, get from device/SDK)
      const fakeBiometricData = "FAKE_FINGERPRINT_BASE64_STRING";
      setCapturedData(fakeBiometricData);

      // Notify parent
      onCapture(fakeBiometricData);

      toast.success("Biometric captured successfully!");
    } catch (err) {
      toast.error("Failed to capture biometric");
      console.error(err);
    } finally {
      setIsCapturing(false);
    }
  };

  return (
    <div className="flex flex-col items-center space-y-3">
      <Button onClick={handleCapture} disabled={isCapturing}>
        {isCapturing ? "Capturing..." : "Capture Biometric"}
      </Button>

      {capturedData && (
        <p className="text-sm text-green-600">✅ Biometric ready</p>
      )}
    </div>
  );
}
