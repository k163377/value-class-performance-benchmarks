package org.wrongwrong.benchmarks.deser

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.wrongwrong.commons.Data
import org.wrongwrong.commons.DataWrapper
import org.wrongwrong.commons.Value
import org.wrongwrong.commons.ValueWrapper
import java.util.concurrent.ThreadLocalRandom
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter

@State(Scope.Benchmark)
class DeserializeBenchmark {
    private val dataConstructor: KFunction<Data> = ::Data
    private val dataParam: KParameter = dataConstructor.parameters.single()
    private val dataWrapperConstructor: KFunction<DataWrapper> = ::DataWrapper
    private val dataWrapperParam: KParameter = dataWrapperConstructor.parameters.single()

    private val valueConstructor: KFunction<Value> = ::Value
    private val valueParam: KParameter = valueConstructor.parameters.single()
    private val valueWrapperConstructor: KFunction<ValueWrapper> = ::ValueWrapper
    private val valueWrapperParam: KParameter = valueWrapperConstructor.parameters.single()

    private var random: Int = -1

    @Setup(Level.Iteration)
    fun setupTarget() {
        random = ThreadLocalRandom.current().nextInt()
    }

    @Benchmark
    fun data1(): DataWrapper = dataConstructor.callBy(mapOf(dataParam to random))
        .let { dataWrapperConstructor.callBy(mapOf(dataWrapperParam to it)) }

    @Benchmark
    fun data2(): DataWrapper = dataWrapperConstructor.callBy(mapOf(dataWrapperParam to Data(random)))

    @Benchmark
    fun data3(): DataWrapper = DataWrapper(Data(random))

    @Benchmark
    fun value1(): ValueWrapper = valueConstructor.callBy(mapOf(valueParam to random))
        .let { valueWrapperConstructor.callBy(mapOf(valueWrapperParam to it)) }

    @Benchmark
    fun value2(): ValueWrapper = valueWrapperConstructor.callBy(mapOf(valueWrapperParam to Value(random)))

    @Benchmark
    fun value3(): ValueWrapper = ValueWrapper(Value(random))
}
