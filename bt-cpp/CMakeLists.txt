cmake_minimum_required(VERSION 3.10)

project(bt-cpp VERSION 1.0)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED True)

add_executable(bt-cpp src/main.cpp)

target_include_directories(bt-cpp PUBLIC "${PROJECT_SOURCE_DIR}/include")