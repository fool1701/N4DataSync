# Report

## Introduction

 The Report module provides facilities for running periodic background reports on a station.

## ReportService

 The ReportService provides a container for the components responsible for generating and routing reports. The process of generating a report is broken down into two components: `BReportSource` and `BReportRecipient`.

## BReport Lifecycle

(ExportSource and EmailRecipient are concrete implementations for ReportSource and ReportRecipient, respectively.)

1.  The `generate` action gets invoked on `BReportSource`. The action can be invoked manually or automatically via the built-in schedule property.
2.  `ReportSource` creates a new `BReport` object which gets propagated to the `ReportRecipient`.
3.  `BReportRecipient` handles routing the report to some destination.
