============================================
Análise do Componente: MainActivity.kt
============================================

O arquivo :file:`MainActivity.kt` serve como o ponto de entrada principal do aplicativo Android. Ele utiliza componentes modernos do Jetpack, como **Compose**, **LifecycleScope** para concorrência (Coroutines) e a API **SplashScreen** para uma inicialização suave.

Classes e Extensões
-------------------

.. code-block:: kotlin

    internal class MainActivity : ComponentActivity() {
        private var isLoading by mutableStateOf(true)
        // ...
    }

* **``MainActivity``**: É a única `Activity` da aplicação, herdando de `ComponentActivity` (base para o Jetpack Compose).
* **``internal``**: O modificador de visibilidade sugere que a classe é visível apenas dentro do módulo (módulo `app`), o que é padrão para componentes de aplicação.
* **Estado ``isLoading``**: Uma variável de estado mutável (`mutableStateOf`) de Kotlin delegate (`by`) usada para controlar a exibição da tela de *splash*.

Gerenciamento de Splash Screen
-------------------------------

O código implementa uma tela de *splash* temporizada usando o ciclo de vida da *Activity* e coroutines:

1.  **Instalação**: ``val splashScreen = installSplashScreen()`` anexa a tela de *splash* nativa.
2.  **Condição de Retenção**: ``splashScreen.setKeepOnScreenCondition { isLoading }`` instrui o sistema a manter a tela de *splash* visível enquanto a variável de estado **``isLoading``** for `true`.
3.  **Temporizador Assíncrono**:
    * `lifecycleScope.launch { ... }`: Inicia uma Coroutine no escopo do ciclo de vida da *Activity*.
    * `delay(400)`: Pausa a Coroutine por 400 milissegundos.
    * `isLoading = false`: Após o atraso, o estado é definido como `false`, liberando a tela de *splash*.

Renderização e Navegação
------------------------

O método **``onCreate``** finaliza configurando a UI do Jetpack Compose:

.. code-block:: kotlin

    enableEdgeToEdge()

    setContent {
        if (!isLoading) {
            CampoMinadoTheme {
                var navHost = NavHost()
                navHost.Create()
            }   
        }
    }

* **``enableEdgeToEdge()``**: Configura a *Activity* para desenhar o conteúdo por trás das barras do sistema (status bar e navigation bar), um padrão moderno de UI.
* **``setContent``**: O bloco Compose que define a hierarquia da UI.
* **Controle de Renderização**: O tema (`CampoMinadoTheme`) e o host de navegação (`NavHost`) só são carregados quando **``!isLoading``** (a tela de *splash* desaparece), garantindo que a UI não seja renderizada antes do tempo de espera mínimo.
* **Inicialização da Navegação**: A linha `navHost.Create()` sugere que a classe **``NavHost``** (presumivelmente localizada em :file:`com.example.campominado.util.NavHost`) é responsável por configurar o grafo de navegação e renderizar a tela inicial do aplicativo (provavelmente a `MainMenuScreen.kt`).
