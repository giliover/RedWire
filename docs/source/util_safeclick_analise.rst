============================================
Análise do Componente: SafeClick.kt
============================================

O arquivo :file:`SafeClick.kt`, localizado no pacote ``com.example.campominado.util``, implementa um padrão de design essencial para a UI: **Debounce** (limitação de frequência). Sua função é impedir que ações sejam executadas múltiplas vezes devido a toques rápidos ou acidentais do usuário.

.. code-block:: kotlin

    internal class SafeClick {
        // ...
    }

* **Classe**: ``SafeClick``
* **Propósito**: Envolver uma função de clique (`action: () -> Unit`) em uma lógica de temporização, garantindo que ela não possa ser chamada novamente dentro de um período definido.
* **Uso**: Esta classe é instanciada e utilizada no componente de botão **``ComponentesUI.kt``**.

Método Create (Lógica de Debounce)
----------------------------------

O método ``Create`` é o ponto central que retorna uma nova função de clique com o recurso de temporização embutido.

.. code-block:: kotlin

    public fun Create(delayMillis: Long = 1000L, action: () -> Unit): () -> Unit {
        val canClick = AtomicBoolean(true)

        return {
            if (canClick.get()) {
                canClick.set(false)
                action()

                CoroutineScope(Dispatchers.Main).launch {
                    delay(delayMillis)
                    canClick.set(true)
                }
            }
        }
    }

* **Controle de Estado Concorrente**: Utiliza **``AtomicBoolean(true)``** para a variável `canClick`. Isso garante que a verificação de estado (`canClick.get()`) e a alteração de estado (`canClick.set(false)`) sejam **thread-safe** e atômicas, evitando *race conditions* mesmo em ambientes multi-threaded.
* **Execução da Ação**: Se `canClick` for `true`, ele imediatamente executa a ação (`action()`) e define `canClick` como `false`.
* **Temporizador Assíncrono**: Um **Coroutine** é iniciado no *Main Dispatcher* para garantir que o **``delay(delayMillis)``** não bloqueie a thread principal da UI.
    * O tempo de atraso padrão é de **1000ms (1 segundo)**.
    * Após o atraso, `canClick` é redefinido para `true`, permitindo um novo clique.

Princípio da Orientação a Objetos (OO)
-------------------------------------

A classe ``SafeClick`` demonstra o princípio da **Encapsulação**:

* A lógica complexa de controle de estado concorrente e temporização assíncrona é completamente **isolada** dentro da classe.
* As classes de UI (como ``ComponentesUI``) não precisam se preocupar com os detalhes da implementação, elas apenas instanciam ``SafeClick`` e fornecem a ação a ser executada, resultando em código mais limpo e manutenção facilitada.

Enfoque em Orientação a Objetos
-------------------------------

* **Objeto responsável por uma única regra**:
  ``SafeClick`` tem uma responsabilidade bem definida: controlar a frequência com que uma ação pode ser executada.
* **Reuso por composição**:
  Qualquer componente de UI pode compor-se com um ``SafeClick`` para ganhar esse comportamento, sem herança nem duplicação de código.
* **Encapsulamento de detalhes concorrentes**:
  Uso de ``AtomicBoolean`` e coroutines fica escondido atrás da interface simples ``Create(...)``, protegendo o restante do código desses detalhes.

Possíveis Melhorias OO
----------------------

* **Interface de estratégia de clique**:
  Criar uma interface (por exemplo, ``ClickStrategy``) e deixar ``SafeClick`` como uma entre várias estratégias possíveis (sem debounce, debounce curto, debounce longo), selecionáveis em tempo de execução.
* **Configuração externa**:
  Receber o ``CoroutineScope`` por parâmetro ou via injeção, em vez de criar um `CoroutineScope(Dispatchers.Main)` interno, facilitando testes e reutilização em outros contextos.
* **Imutabilidade da configuração**:
  Transformar ``delayMillis`` em uma propriedade imutável de instância (passado no construtor) para representar claramente um \"tipo\" de clique seguro com configuração fixa.
