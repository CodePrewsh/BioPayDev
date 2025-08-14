"use client"

import * as React from "react"
import { cn } from "@/lib/utils"

interface ProgressProps extends React.HTMLAttributes<HTMLDivElement> {
  value?: number
  max?: number
  showLabel?: boolean
  className?: string
}

const Progress = React.forwardRef<HTMLDivElement, ProgressProps>(
  ({ value = 0, max = 100, showLabel = false, className, ...props }, ref) => {
    const percentage = Math.min(Math.max((value / max) * 100, 0), 100)

    return (
      <div
        ref={ref}
        data-slot="progress-root"
        className={cn(
          "relative w-full h-4 rounded-full bg-gray-200 overflow-hidden",
          className
        )}
        {...props}
      >
        <div
          data-slot="progress-indicator"
          className="h-full bg-blue-600 transition-all duration-300"
          style={{ width: `${percentage}%` }}
        />
        {showLabel && (
          <span
            data-slot="progress-label"
            className="absolute inset-0 flex items-center justify-center text-xs font-medium text-white"
          >
            {Math.round(percentage)}%
          </span>
        )}
      </div>
    )
  }
)
Progress.displayName = "Progress"

// Optional: separate slot components for more Radix-like structure
function ProgressRoot({
  className,
  ...props
}: React.HTMLAttributes<HTMLDivElement>) {
  return <div data-slot="progress-root" className={cn("w-full", className)} {...props} />
}

function ProgressIndicator({
  value,
  max = 100,
  className,
  ...props
}: { value: number; max?: number } & React.HTMLAttributes<HTMLDivElement>) {
  const percentage = Math.min(Math.max((value / max) * 100, 0), 100)
  return (
    <div
      data-slot="progress-indicator"
      className={cn("h-full bg-blue-600 transition-all duration-300", className)}
      style={{ width: `${percentage}%` }}
      {...props}
    />
  )
}

function ProgressLabel({
  children,
  className,
  ...props
}: React.HTMLAttributes<HTMLSpanElement>) {
  return (
    <span
      data-slot="progress-label"
      className={cn("absolute inset-0 flex items-center justify-center text-xs font-medium text-white", className)}
      {...props}
    >
      {children}
    </span>
  )
}

export { Progress, ProgressRoot, ProgressIndicator, ProgressLabel }
