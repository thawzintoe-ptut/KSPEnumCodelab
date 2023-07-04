package com.ptut.processor

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

// TODO: step 8 create SymbolProcessorProvider for Enum generate
class EnumGenerateProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        // TODO: step 8 create SymbolProcessor for Enum generate
        return EnumGenerateProcessor(environment.codeGenerator)
    }
}