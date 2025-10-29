===================================
Estrutura de Arquivos do Projeto
===================================

O projeto *REDWIRE* (Campo Minado) segue a estrutura padrão de projetos Android, com foco na organização do código-fonte em pacotes que refletem a **Programação Orientada a Objetos (OO)** e o padrão de design da interface do usuário.

::

    .
    ├── app/
    │   └── src/
    │       └── main/
    │           ├── java/
    │           │   └── com/example/campominado/
    │           │       ├── ui/
    │           │       │   ├── screens/
    │           │       │   │   ├── ComponentesUI.kt
    │           │       │   │   ├── GameScreen.kt
    │           │       │   │   └── MainMenuScreen.kt
    │           │       │   ├── theme/
    │           │       │   ├── utils/
    │           │       │   └── MainActivity.kt
    │           ├── res/
    │           └── AndroidManifest.xml
    └── ... (arquivos de configuração como .gradle, build.gradle.kts, etc.)

Conteúdo Principal (``app/src/main/java/com/example/campominado/``)
-------------------------------------------------------------------

Este pacote contém o código-fonte principal em **Kotlin**, organizado em subdiretórios lógicos:

* :file:`MainActivity.kt`:
    O ponto de entrada principal da aplicação Android. Geralmente, inicializa a interface do usuário e pode orquestrar a navegação.
* :file:`model/`:
    Pacote de **Domínio/Lógica de Negócio**, contendo as classes fundamentais da Orientação a Objetos do jogo.
    * :file:`Celula.kt`: Representa a unidade básica do Campo Minado (a célula, com estado e conteúdo).
* :file:`ui/`:
    Contém a lógica e a definição de componentes relacionados à **Interface do Usuário (UI)**.
* :file:`theme/`:
    Inclui arquivos de tema e estilo, como :file:`Color.kt` e :file:`Type.kt`, para padronização visual da aplicação.
* :file:`utils/`:
    Armazena classes e funções auxiliares que não se encaixam nas categorias de UI ou *Domain* (Lógica de Negócio, que presumivelmente estaria em um pacote separado se a arquitetura fosse mais complexa, ou estaria implementada dentro de outras classes).

Pacote ``ui/screens/``
-----------------------

A organização em *screens* sugere uma abordagem moderna de UI (possivelmente utilizando **Jetpack Compose**), onde a interface é dividida em componentes de tela distintos:

* :file:`MainMenuScreen.kt`:
    Define a tela principal do menu do jogo (iniciar novo jogo, opções, etc.).
* :file:`GameScreen.kt`:
    Define a tela onde o jogo Campo Minado é jogado. Espera-se que esta tela interaja com a **lógica de jogo orientada a objetos**.
* :file:`ComponentesUI.kt`:
    Pode conter componentes de UI reutilizáveis, como botões, células do campo minado, ou diálogos.
* :file:`CampoMinadoGrid.kt`:
    Presumivelmente o componente de UI responsável por renderizar o tabuleiro (a grade de células) e interagir com a lógica do **``model``**.

Pacote ``res/`` (Recursos)
--------------------------

* :file:`drawable/`:
    Recursos visuais (ícones, imagens).
* :file:`values/`:
    Definições de cores, *strings* de texto, e estilos.
* :file:`xml/`:
    Arquivos de configuração ou recursos em formato XML.
