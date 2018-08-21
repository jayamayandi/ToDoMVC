#!/bin/bash
cd ./ToDoMVC
docker build -t todomvc-auto:v1 .
docker compose up -d