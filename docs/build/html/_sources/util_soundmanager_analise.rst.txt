==============================================
Análise do Utilitário: SoundManager.kt
==============================================

O arquivo :file:`SoundManager.kt`, no pacote ``com.example.campominado.util``,
encapsula toda a lógica de **áudio** do aplicativo, incluindo música de fundo e
efeitos sonoros (como o som da bomba).

Visão Geral
-----------

.. code-block:: kotlin

    class SoundManager(private val context: Context) {
        private var backgroundMusic: MediaPlayer? = null
        private var bombSound: MediaPlayer? = null

        fun playBackgroundMusic(resourceId: Int) { ... }
        fun stopBackgroundMusic() { ... }
        fun playBombSound(resourceId: Int) { ... }
        fun release() { ... }
    }

* **Responsabilidade**: Controlar o ciclo de vida dos objetos ``MediaPlayer``.
* **Contexto**: Recebe um ``Context`` Android para conseguir carregar recursos
  de áudio via `MediaPlayer.create`.

Controle da Música de Fundo
---------------------------

.. code-block:: kotlin

    fun playBackgroundMusic(resourceId: Int) {
        try {
            stopBackgroundMusic()
            backgroundMusic = MediaPlayer.create(context, resourceId)
            backgroundMusic?.apply {
                isLooping = true
                setVolume(0.5f, 0.5f)
                start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun stopBackgroundMusic() {
        backgroundMusic?.release()
        backgroundMusic = null
    }

* **Substituição Segura**: Sempre chama `stopBackgroundMusic()` antes de iniciar
  uma nova música, evitando múltiplos `MediaPlayer` ativos.
* **Loop Contínuo**: Ativa `isLooping = true` para que a trilha toque em ciclo.
* **Volume Padronizado**: Define um volume médio (0.5f) para não sobrepor a UI.

Efeito Sonoro da Bomba
----------------------

.. code-block:: kotlin

    fun playBombSound(resourceId: Int) {
        try {
            bombSound?.release()
            bombSound = MediaPlayer.create(context, resourceId)
            bombSound?.apply {
                setVolume(1.0f, 1.0f)
                start()
                setOnCompletionListener {
                    it.release()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

* **Som de Curta Duração**: Cria um novo `MediaPlayer` para cada explosão.
* **Liberação Automática**: Usa `setOnCompletionListener` para liberar o
  recurso após o término do som.

Liberação de Recursos
---------------------

.. code-block:: kotlin

    fun release() {
        stopBackgroundMusic()
        bombSound?.release()
        bombSound = null
    }

* **Boas Práticas Android**: Evita *memory leaks* e uso indevido de recursos
  de mídia, liberando tudo quando a tela é destruída.

Integração com Compose: rememberSoundManager
--------------------------------------------

Além da classe principal, o arquivo define um helper composable:

.. code-block:: kotlin

    @Composable
    fun rememberSoundManager(): SoundManager {
        val context = LocalContext.current
        val soundManager = remember { SoundManager(context) }

        DisposableEffect(soundManager) {
            onDispose {
                soundManager.release()
            }
        }
        return soundManager
    }

- **Integração com o Ciclo de Vida Compose**: `remember` garante uma única instância de ``SoundManager`` por composição e `DisposableEffect` chama `release()` automaticamente quando o Composable sai de cena.
- **Uso nas Telas**: telas como ``MainMenuScreen`` e ``GameScreen`` apenas chamam `rememberSoundManager()` para obter uma instância pronta para uso, mantendo a UI desacoplada dos detalhes de áudio.

Enfoque em Orientação a Objetos
-------------------------------

- **Classe de serviço**: ``SoundManager`` encapsula todo o comportamento relacionado a áudio (música de fundo e efeitos), aplicando **responsabilidade única**.
- **Composição com a UI**: as telas não precisam conhecer ``MediaPlayer`` nem detalhes de ciclo de vida; elas compõem seu comportamento com um objeto de som de alto nível.
- **Gerenciamento de recursos como comportamento de objeto**: métodos como ``playBackgroundMusic``, ``stopBackgroundMusic`` e ``release`` traduzem operações de baixo nível em uma interface OO expressiva.

Possíveis Melhorias OO
----------------------

- **Interface de áudio**: definir uma interface (por exemplo, ``SoundPlayer``) que ``SoundManager`` implementa, permitindo substituição por implementações falsas (fake) em testes ou por outras bibliotecas de áudio no futuro.
- **Separar responsabilidades de trilha e efeitos**: dividir em duas classes (`BackgroundMusicManager`, `EffectsPlayer`) quando a lógica crescer, mantendo a coesão.
- **Configuração orientada a objetos**: encapsular parâmetros como volume padrão, estratégia de repetição e priorização de sons em um objeto de configuração (`SoundConfig`) passado ao construtor.


