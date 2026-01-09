package soft.exe.colabora.study.core.repository

import kotlinx.coroutines.delay
import soft.exe.colabora.study.core.entity.Answer
import soft.exe.colabora.study.core.entity.Question

class LocalQuestionsRepository : QuestionsRepository {
    override suspend fun getQuestions(prompt: String): List<Question> {
        delay(3000)
        return listOf(
            Question(
                id = 1,
                question = "Who originally created **Java**?",
                answers = listOf(
                    Answer(id = 0, text = "**Sun Microsystems** ☀️", correct = true),
                    Answer(id = 1, text = "**Oracle**", correct = false),
                    Answer(id = 2, text = "**JetBrains**", correct = false),
                )
            ),
            Question(
                id = 2,
                question = "What was Java's original **name**?\n > Fun historical fact",
                answers = listOf(
                    Answer(id = 0, text = "**Oak** 🌳", correct = true),
                    Answer(id = 1, text = "Pine", correct = false),
                    Answer(id = 2, text = "Coffee", correct = false),
                )
            ),
            Question(
                id = 3,
                question = "Why was **Java** designed to be *portable*?",
                answers = listOf(
                    Answer(id = 0, text = "To run on **any platform** via the JVM", correct = true),
                    Answer(id = 1, text = "To replace **C** completely", correct = false),
                    Answer(id = 2, text = "To be faster than **assembly**", correct = false),
                )
            ),
            Question(
                id = 4,
                question = "What does the **JVM** stand for?",
                answers = listOf(
                    Answer(id = 0, text = "**Java Virtual Machine**", correct = true),
                    Answer(id = 1, text = "Java Verified Module", correct = false),
                    Answer(id = 2, text = "Joint Virtual Memory", correct = false),
                )
            ),
            Question(
                id = 5,
                question = "Which company currently **maintains** Java?",
                answers = listOf(
                    Answer(id = 0, text = "**Oracle** 🏢", correct = true),
                    Answer(id = 1, text = "Google", correct = false),
                    Answer(id = 2, text = "JetBrains", correct = false),
                )
            ),
            Question(
                id = 6,
                question = "Why was **Kotlin** created?\n > Think about *developer pain*",
                answers = listOf(
                    Answer(id = 0, text = "To fix **Java verbosity** and null-safety issues", correct = true),
                    Answer(id = 1, text = "To replace **Python**", correct = false),
                    Answer(id = 2, text = "To avoid the **JVM**", correct = false),
                )
            ),
            Question(
                id = 7,
                question = "Who developed **Kotlin**?",
                answers = listOf(
                    Answer(id = 0, text = "**JetBrains** ✨", correct = true),
                    Answer(id = 1, text = "Oracle", correct = false),
                    Answer(id = 2, text = "Microsoft", correct = false),
                )
            ),
            Question(
                id = 8,
                question = "In which year was **Kotlin** officially released?",
                answers = listOf(
                    Answer(id = 0, text = "**2016**", correct = true),
                    Answer(id = 1, text = "2010", correct = false),
                    Answer(id = 2, text = "2020", correct = false),
                )
            ),
            Question(
                id = 9,
                question = "What major feature does **Kotlin** introduce for safety?",
                answers = listOf(
                    Answer(id = 0, text = "**Null safety** 🚫", correct = true),
                    Answer(id = 1, text = "Manual memory management", correct = false),
                    Answer(id = 2, text = "Pointers", correct = false),
                )
            ),
            Question(
                id = 10,
                question = "Why did **Google** adopt Kotlin for Android?\n > Strategic decision",
                answers = listOf(
                    Answer(id = 0, text = "Better **productivity** and safety", correct = true),
                    Answer(id = 1, text = "Java was **deprecated**", correct = false),
                    Answer(id = 2, text = "Kotlin is faster than **C++**", correct = false),
                )
            ),
            Question(
                id = 11,
                question = "What is **Kotlin Multiplatform**?",
                answers = listOf(
                    Answer(id = 0, text = "A way to share **business logic** across platforms", correct = true),
                    Answer(id = 1, text = "A UI framework only", correct = false),
                    Answer(id = 2, text = "A replacement for **Flutter**", correct = false),
                )
            ),
            Question(
                id = 12,
                question = "Which platforms does **Kotlin Multiplatform** target?",
                answers = listOf(
                    Answer(id = 0, text = "**JVM, Android, iOS, JS, Native**", correct = true),
                    Answer(id = 1, text = "Android only", correct = false),
                    Answer(id = 2, text = "Web only", correct = false),
                )
            ),
            Question(
                id = 13,
                question = "What problem does **Kotlin Multiplatform** solve?\n > Architecture focused",
                answers = listOf(
                    Answer(id = 0, text = "Code **duplication** 🔁", correct = true),
                    Answer(id = 1, text = "GPU rendering", correct = false),
                    Answer(id = 2, text = "Database sharding", correct = false),
                )
            ),
            Question(
                id = 14,
                question = "What is **Gradle**?",
                answers = listOf(
                    Answer(id = 0, text = "A **build automation** tool ⚙️", correct = true),
                    Answer(id = 1, text = "A programming language", correct = false),
                    Answer(id = 2, text = "A JVM replacement", correct = false),
                )
            ),
            Question(
                id = 15,
                question = "Why did **Gradle** become popular over Maven?",
                answers = listOf(
                    Answer(id = 0, text = "More **flexible** and faster builds", correct = true),
                    Answer(id = 1, text = "Uses XML only", correct = false),
                    Answer(id = 2, text = "Requires no configuration", correct = false),
                )
            ),
            Question(
                id = 16,
                question = "Which languages can be used in **Gradle scripts**?",
                answers = listOf(
                    Answer(id = 0, text = "**Groovy** and **Kotlin DSL**", correct = true),
                    Answer(id = 1, text = "Java only", correct = false),
                    Answer(id = 2, text = "Python", correct = false),
                )
            ),
            Question(
                id = 17,
                question = "What does **Gradle** use to improve build speed?",
                answers = listOf(
                    Answer(id = 0, text = "**Incremental builds** and caching 🚀", correct = true),
                    Answer(id = 1, text = "Rebuilding everything", correct = false),
                    Answer(id = 2, text = "Manual compilation", correct = false),
                )
            ),
            Question(
                id = 18,
                question = "How is **Kotlin DSL** beneficial?",
                answers = listOf(
                    Answer(id = 0, text = "**Type safety** and IDE support", correct = true),
                    Answer(id = 1, text = "Less readable scripts", correct = false),
                    Answer(id = 2, text = "No auto-completion", correct = false),
                )
            ),
            Question(
                id = 19,
                question = "What role does **JetBrains** play in the JVM ecosystem?",
                answers = listOf(
                    Answer(id = 0, text = "Tooling and **language innovation**", correct = true),
                    Answer(id = 1, text = "Hardware manufacturing", correct = false),
                    Answer(id = 2, text = "Cloud hosting", correct = false),
                )
            ),
            Question(
                id = 20,
                question = "Why is **Java** still widely used today?\n > Longevity question",
                answers = listOf(
                    Answer(id = 0, text = "Strong **ecosystem** and stability", correct = true),
                    Answer(id = 1, text = "Because it never changes", correct = false),
                    Answer(id = 2, text = "Because it's the fastest language", correct = false),
                )
            ),
            Question(
                id = 21,
                question = "What does **backward compatibility** mean in Java?",
                answers = listOf(
                    Answer(id = 0, text = "Old code still **runs** on new versions", correct = true),
                    Answer(id = 1, text = "New code runs on old JVMs", correct = false),
                    Answer(id = 2, text = "APIs are removed often", correct = false),
                )
            ),
            Question(
                id = 22,
                question = "Why is **Kotlin** fully interoperable with Java?",
                answers = listOf(
                    Answer(id = 0, text = "They both target the **JVM**", correct = true),
                    Answer(id = 1, text = "They share the same syntax", correct = false),
                    Answer(id = 2, text = "They compile to C++", correct = false),
                )
            ),
            Question(
                id = 23,
                question = "What is a major **design philosophy** of Kotlin?",
                answers = listOf(
                    Answer(id = 0, text = "**Pragmatism** over theory", correct = true),
                    Answer(id = 1, text = "Academic experimentation", correct = false),
                    Answer(id = 2, text = "Minimal tooling", correct = false),
                )
            ),
            Question(
                id = 24,
                question = "What is the **main risk** of large Gradle builds?",
                answers = listOf(
                    Answer(id = 0, text = "Complex **configuration** 🧩", correct = true),
                    Answer(id = 1, text = "Lack of features", correct = false),
                    Answer(id = 2, text = "No community support", correct = false),
                )
            ),
            Question(
                id = 25,
                question = "What is **Gradle Wrapper** used for?",
                answers = listOf(
                    Answer(id = 0, text = "Ensuring **same Gradle version** for everyone", correct = true),
                    Answer(id = 1, text = "Packaging Java apps", correct = false),
                    Answer(id = 2, text = "Running tests only", correct = false),
                )
            ),
            Question(
                id = 26,
                question = "Why is **Kotlin Multiplatform** not \"write once, run anywhere\"?",
                answers = listOf(
                    Answer(id = 0, text = "Each platform still needs **platform-specific code**", correct = true),
                    Answer(id = 1, text = "It cannot compile to iOS", correct = false),
                    Answer(id = 2, text = "It lacks tooling", correct = false),
                )
            ),
            Question(
                id = 27,
                question = "What does **Gradle** model builds as?",
                answers = listOf(
                    Answer(id = 0, text = "A **task dependency graph**", correct = true),
                    Answer(id = 1, text = "Linear scripts", correct = false),
                    Answer(id = 2, text = "Shell commands", correct = false),
                )
            ),
            Question(
                id = 28,
                question = "Why is **Java** often chosen for enterprise systems?",
                answers = listOf(
                    Answer(id = 0, text = "**Scalability** and long-term support", correct = true),
                    Answer(id = 1, text = "Short-lived projects", correct = false),
                    Answer(id = 2, text = "Game development only", correct = false),
                )
            ),
            Question(
                id = 29,
                question = "How did **Kotlin** influence modern Java?",
                answers = listOf(
                    Answer(id = 0, text = "Inspired features like **var** and records", correct = true),
                    Answer(id = 1, text = "Removed the JVM", correct = false),
                    Answer(id = 2, text = "Deprecated OOP", correct = false),
                )
            ),
            Question(
                id = 30,
                question = "What is the **future trend** of the JVM ecosystem?\n > Big picture",
                answers = listOf(
                    Answer(id = 0, text = "Multiple languages sharing the **same runtime** 🌍", correct = true),
                    Answer(id = 1, text = "Java being abandoned", correct = false),
                    Answer(id = 2, text = "Native-only development", correct = false),
                )
            ),
        )
    }
}