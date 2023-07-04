package com.ptut.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.validate
import com.ptut.annotation.GenerateEnum
import kotlin.reflect.KClass

class EnumGenerateProcessor(private val codeGenerator: CodeGenerator) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        // TODO step 25: register visitor class
        val visitor = EnumGenerateVisitor(codeGenerator)
        // TODO step 10 : Validating Symbols
        val symbols = resolver.getSymbols(GenerateEnum::class)
        val validatedSymbols = symbols.filter { it.validate() }.toList()
        validatedSymbols.forEach { symbol ->
            // TODO : step 11 -> create KSVisitorVoid to check Hierarchy
            symbol.accept(visitor,Unit)
        }
        return symbols.toList() - validatedSymbols.toSet()
    }

    // TODO: step 9: create getSymbols extension with Kotlin reflection
    private fun Resolver.getSymbols(cls: KClass<*>) =
        this.getSymbolsWithAnnotation(cls.qualifiedName.orEmpty())
            .filterIsInstance<KSClassDeclaration>()
            .filter(KSNode::validate)

    /**
     * TODO step 26: Registering the Provider "src/main/resources/META-INF/services"
     * create file "com.google.devtools.ksp.processing.SymbolProcessorProvider"
     * Register SymbolProcessorProvider
     */
}
