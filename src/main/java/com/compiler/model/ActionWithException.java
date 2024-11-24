package com.compiler.model;

@FunctionalInterface
interface ActionWithException {
    void run() throws SemanticError;
}

