package com.ptut.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.writeTo
import java.util.Locale

/**
 * TODO step 15: create enumValueType(type and enumConstants)
 * type -> private val type: Int for Enum class
 * enumConstants -> parameter from "Enum" annotation to get how many enum constants for this
 */

class EnumGenerateVisitorFromProperty(
    private val logger: KSPLogger,
    private val codeGenerator: CodeGenerator,
) : KSVisitorVoid() {

    private val enumClass = Enum::class.simpleName

    override fun visitPropertyDeclaration(property: KSPropertyDeclaration, data: Unit) {
        generateEnumClassForProperty(property, packageName = property.packageName.getShortName())
    }

    /**
     * TODO step 16: using kotlin poet for code generation enum class
     * Read more for code generation -> https://square.github.io/kotlinpoet/
     */
    private fun generateEnumClassForProperty(property: KSPropertyDeclaration, packageName: String) {
        val enumAnnotation =
            property.annotations.find { it.shortName.asString() == enumClass }
        // TODO step 18: get arguments from annotated class parameter

        // Get parameters (Male, Female, Other) from declare Enum annotation
        val listArguments = enumAnnotation?.arguments?.find { arg ->
            arg.name?.asString() == enumConstants
        }?.value as List<*>

        // TODO step 19: create class from property field name (capitalize for first letter because it is class name)
        val propertyName = property.simpleName.asString()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        // get converted enum class name 'GenderType'

        // get package converted enum class name from property (var genderType: Int)
        val enumClassName = ClassName(packageName, propertyName)

        /**
         * TODO step 20: type is the parameter for Generated enumClass using FunSpec.constructorBuilder()
         * public enum class AgentType(
         *   private val type: Int
         * )
         */
        // to generate "public constructor(type: kotlin.Int)"
        val type = FunSpec.constructorBuilder()
            .addParameter("type", Int::class)
            .build()

        // TODO step 21: create enumClass using TypeSpec.enumBuilder
        val enumClass = TypeSpec.enumBuilder(propertyName)
            .primaryConstructor(type)
            .addProperty(
                PropertySpec.builder("type", Int::class)
                    .initializer("type")
                    .addModifiers(KModifier.PRIVATE)
                    .build(),
            )
            .apply {
                // TODO step 22: create enum constant with All letters with uppercase
                listArguments.forEachIndexed { index, value ->
                    val enumConstantValueIndex = TypeSpec.anonymousClassBuilder()
                        .addSuperclassConstructorParameter("%L", index)
                        .build()
                    addEnumConstant(
                        value.toString().uppercase(),
                        enumConstantValueIndex,
                    ).build()
                }
                /**
                 * TODO step 23: create mapper method for mapping enum from Integer
                 * public companion object {
                 *     public fun fromInt(type: Int): AgentType = values().first { it.type == type }
                 *   }
                 */
                addType(
                    TypeSpec.companionObjectBuilder()
                        .addFunction(
                            FunSpec.builder("fromInt")
                                .addParameter(enumValueType, Int::class)
                                .returns(enumClassName)
                                .addCode(
                                    """
                                        return values().first { it.$enumValueType == $enumValueType }
                                    """.trimIndent(),
                                )
                                .build(),
                        )
                        .build(),
                )
            }
            .build()

        // TODO step 24: generate Enum class file with FileSpec
        val fileSpec = FileSpec.builder(packageName, propertyName).apply {
            addType(enumClass)
        }.build()

        fileSpec.writeTo(codeGenerator, false)
    }
}
