package machine

fun main() {
    val coffeeMachine = CoffeeMachine()
    coffeeMachine.control("CHOICE_OF_ACTION")

    while (coffeeMachine.currentState != CoffeeMachine.State.EXIT) {
        coffeeMachine.control(readLine()!!)
    }
}

class CoffeeMachine {
    var qtyWater = 400
    var qtyMilk = 540
    var qtyCoffeeBeans = 120
    var qtyDisposableCups = 9
    var qtyMoney = 550

    var isEnoughWater = false
    var isEnoughMilk = false
    var isCoffeeBeans = false
    var isDisposableCups = false

    companion object {
        val qtyWaterForEspresso = 250
        val qtyMilkForEspresso = 0
        val qtyCoffeeBeansForEspresso = 16
        val qtyDisposableCupsForEspresso = 1
        val qtyMoneyForEspresso = 4

        val qtyWaterForLatte = 350
        val qtyMilkForLatte = 75
        val qtyCoffeeBeansForLatte = 20
        val qtyDisposableCupsForLatte = 1
        val qtyMoneyForLatte = 7

        val qtyWaterForCappuccino = 200
        val qtyMilkForCappuccino = 100
        val qtyCoffeeBeansForCappuccino = 12
        val qtyDisposableCupsForCappuccino = 1
        val qtyMoneyForCappuccino = 6
    }

    var currentState = State.CHOICE_OF_ACTION

    enum class State {
        CHOICE_OF_ACTION,
        COFFEE_SELECTION,
        BUY,
        FILL,
        FILL_WATER,
        FILL_MILK,
        FILL_COFFEE_BEANS,
        FILL_CUPS,
        TAKE,
        REMAINING,
        EXIT,
        NULL;

        companion object {
            fun findByName(name: String): State {
                for (state in State.values()) {
                    if (name.toUpperCase() == state.name) return state
                }
                return State.NULL
            }
        }
    }

    fun control(input: String) {
        if (currentState == State.CHOICE_OF_ACTION ||
            currentState == State.BUY ||
            currentState == State.FILL ||
            currentState == State.TAKE ||
            currentState == State.REMAINING ||
            currentState == State.EXIT
        // Для этих состояний input - строка (название следующего состояния). Для остальных состояний input - число
        ) {
            currentState = State.findByName(input)
        }

        while (currentState != State.EXIT) {
            when (currentState) {
                State.CHOICE_OF_ACTION -> {
                    print("Write action (buy, fill, take, remaining, exit): > ")
                    return
                }
                State.BUY -> {
                    println()
                    print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: > ")
                    currentState = State.COFFEE_SELECTION
                    return
                }
                State.COFFEE_SELECTION -> {
                    currentState = State.CHOICE_OF_ACTION
                    when (input) {
                        "1" -> {
                            isEnoughWater = qtyWater >= qtyWaterForEspresso
                            isEnoughMilk = qtyMilk >= qtyMilkForEspresso
                            isCoffeeBeans = qtyCoffeeBeans >= qtyCoffeeBeansForEspresso
                            isDisposableCups = qtyDisposableCups >= qtyDisposableCupsForEspresso

                            if (isEnoughWater && isEnoughMilk && isCoffeeBeans && isDisposableCups) {
                                qtyWater -= qtyWaterForEspresso
                                qtyMilk -= qtyMilkForEspresso
                                qtyCoffeeBeans -= qtyCoffeeBeansForEspresso
                                qtyDisposableCups -= qtyDisposableCupsForEspresso
                                qtyMoney += qtyMoneyForEspresso
                            }
                        }
                        "2" -> {
                            isEnoughWater = qtyWater >= qtyWaterForLatte
                            isEnoughMilk = qtyMilk >= qtyMilkForLatte
                            isCoffeeBeans = qtyCoffeeBeans >= qtyCoffeeBeansForLatte
                            isDisposableCups = qtyDisposableCups >= qtyDisposableCupsForLatte

                            if (isEnoughWater && isEnoughMilk && isCoffeeBeans && isDisposableCups) {
                                qtyWater -= qtyWaterForLatte
                                qtyMilk -= qtyMilkForLatte
                                qtyCoffeeBeans -= qtyCoffeeBeansForLatte
                                qtyDisposableCups -= qtyDisposableCupsForLatte
                                qtyMoney += qtyMoneyForLatte
                            }
                        }
                        "3" -> {
                            isEnoughWater = qtyWater >= qtyWaterForCappuccino
                            isEnoughMilk = qtyMilk >= qtyMilkForCappuccino
                            isCoffeeBeans = qtyCoffeeBeans >= qtyCoffeeBeansForCappuccino
                            isDisposableCups = qtyDisposableCups >= qtyDisposableCupsForCappuccino

                            if (isEnoughWater && isEnoughMilk && isCoffeeBeans && isDisposableCups) {
                                qtyWater -= qtyWaterForCappuccino
                                qtyMilk -= qtyMilkForCappuccino
                                qtyCoffeeBeans -= qtyCoffeeBeansForCappuccino
                                qtyDisposableCups -= qtyDisposableCupsForCappuccino
                                qtyMoney += qtyMoneyForCappuccino
                            }
                        }
                        "back" -> {
                           continue
                        }
                    }
                    when {
                        !isEnoughWater -> println("Sorry, not enough water!")
                        !isEnoughMilk -> println("Sorry, not enough milk!")
                        !isCoffeeBeans -> println("Sorry, not coffee beans!")
                        !isDisposableCups -> println("Sorry, not disposable cups!")
                        else -> println("I have enough resources, making you a coffee!")
                    }
                    println()
                }
                State.FILL -> {
                    println()
                    print("Write how many ml of water do you want to add: > ")
                    currentState = State.FILL_WATER
                    return
                }
                State.FILL_WATER -> {
                    qtyWater += input.toInt()
                    print("Write how many ml of milk do you want to add: > ")
                    currentState = State.FILL_MILK
                    return
                }
                State.FILL_MILK -> {
                    qtyMilk += input.toInt()
                    print("Write how many grams of coffee beans do you want to add: > ")
                    currentState = State.FILL_COFFEE_BEANS
                    return
                }
                State.FILL_COFFEE_BEANS -> {
                    qtyCoffeeBeans += input.toInt()
                    print("Write how many disposable cups of coffee do you want to add: > ")
                    currentState = State.FILL_CUPS
                    return
                }
                State.FILL_CUPS -> {
                    qtyDisposableCups += input.toInt()
                    currentState = State.CHOICE_OF_ACTION
                }
                State.TAKE -> {
                    println()
                    println("I gave you \$$qtyMoney")
                    println()
                    qtyMoney = 0
                    currentState = State.CHOICE_OF_ACTION
                }
                State.REMAINING -> {
                    printState(qtyWater, qtyMilk, qtyCoffeeBeans, qtyDisposableCups, qtyMoney)
                    currentState = State.CHOICE_OF_ACTION
                }
                State.EXIT -> {
                    currentState = State.EXIT
                    return
                }
            }
        }
    }

    fun printState(qtyWater: Int, qtyMilk: Int, qtyCoffeeBeans: Int, qtyDisposableCups: Int, qtyMoney: Int) {
        println()
        println("The coffee machine has:")
        println("$qtyWater of water")
        println("$qtyMilk of milk")
        println("$qtyCoffeeBeans of coffee beans")
        println("$qtyDisposableCups of disposable cups")
        println("\$$qtyMoney of money")
        println()
    }
}