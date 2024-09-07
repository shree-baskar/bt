cmake_minimum_required(VERSION 3.10)

project(bt-cpp)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED True)

set(SOURCES
    src/main.cpp
    src/Transaction.cpp
    src/Tracker.cpp
)

add_executable(bt-cpp ${SOURCES})

target_include_directories(bt-cpp PUBLIC "${PROJECT_SOURCE_DIR}/include")