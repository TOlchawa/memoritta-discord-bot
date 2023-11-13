# Discord ChatGPT Integration Project

## Description
This project integrates Discord with ChatGPT in a unique way. It features a bot that connects to a Discord server, a service for temporarily storing data in a local MongoDB database, and another service that retrieves data from MongoDB. This data is then used as context for querying ChatGPT to generate summaries of discussions from selected Discord channels.

## Features
* Discord Bot: Connects to a Discord server and listens for commands or discussions.
* Temporary Data Storage: Utilizes a local MongoDB database to store data temporarily.
* Data Retrieval Service: Extracts data from MongoDB to provide context for ChatGPT queries.
* ChatGPT Integration: Uses ChatGPT to create summaries of Discord channel discussions based on the provided context.