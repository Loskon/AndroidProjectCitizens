## Citizens for Android  <a name="paragraph0"></a>

Citizens - представляет собой приложение для показа случайно сгенерированного списка граждан. Данное приложение было сделано в качестве тестового задания.

### Содержание

1. [Описание тестового задания](#paragraph1)
    * [Цель](#subparagraph1-1)
    * [Задача](#subparagraph1-2)
    * [Требования](#subparagraph1-3)
    * [Полное описание](#subparagraph1-4)
2. [Описание приложения](#paragraph2)
    * [Скриншоты](#subparagraph2-1)
3. [Контакты](#paragraph3)
    
## Описание тестового задания <a name="paragraph1"></a>

### Цель <a name="subparagraph1-1"></a>

Данное тестовое задание призвано проверить знания и навыки по следующим направлениям:
* Внимательное чтение и изучение требований задания.
* Структурирование логики Android-приложения.
* Организация многопоточного и асинхронного взаимодействия компонентов
приложения.
* Гайдлайны Material Design и применение их для организации комфортного
взаимодействия пользователя с приложением.

***

### Задача <a name="subparagraph1-2"></a>

Требуется разработать Android-приложение для просмотра информации о гражданах
выбранного города, сгенерированной случайным образом.
Приложение состоит из 2-х компонентов:
1. Генератор данных граждан.
2. UI (пользовательский интерфейс).

***

### Требования <a name="subparagraph1-3"></a>

*Генератор данных граждан:*

* Генерация списка граждан должна происходить в потоке, отличном от потока
пользовательского интерфейса.
* Получение сгенерированного списка должно осуществляться с помощью службы
(Service).

*Пользовательский интерфейс:*

* Интерфейс должен быть удобным и понятным пользователю.
* При запросах пользовательского интерфейса к генератору на получение списка
пользовательский интерфейс не должен блокироваться.
* Пользовательский интерфейс, включая настройки генератора и список граждан
должен быть спроектирован в соответствии с Material Design.
* Вёрстка должна выполняться с использованием ConstraintLayout.
* Реализация списка выполняется с использованием RecyclerView.

*Общие:*

* Обязательные:
   * Проект должен быть выполнен в актуальной версии Android Studio.
   * Минимальная поддерживаемая версия Android: 6.0.
   * Запрещается использование сторонних библиотек (вроде RxJava).

* Дополнительные:
   * Применение паттерна Single Activity с использованием компонентов Fragment.
   * Применение паттерна MVVM.

***

### Полное описание <a name="subparagraph1-4"></a>

С полным описанием тестового задания можно ознакомиться по данной [ссылке].

[ссылке]: https://drive.google.com/file/d/1GgAKOxKFG26vKMeAWZ4Lyar0gLhgETOC/view?usp=sharing

***

## Описание приложения <a name="paragraph2"></a>

### Скриншоты <a name="subparagraph2-1"></a>

<div align="center">
   
| <img src="screenshots/screen1.jpg" width="250"> | <img src="screenshots/screen2.jpg" width="250"> | <img src="screenshots/screen3.jpg" width="250"> |
| :---------------------------------------------: | :---------------------------------------------: | :---------------------------------------------: |
| Список граждан                                  | Данные гражданина                               | Настройки                                       |
   
</div>

## Контакты <a name="paragraph3"></a>

[![Telegram](https://img.shields.io/badge/Telegram-2CA5E0?style=for-the-badge&logo=telegram&logoColor=white)](https://t.me/loskon)
[![Email](https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white)](mailto:andreyrochev23@gmail.com)


[Вернуться к описанию :arrow_up:](#paragraph0)
