[![Build status](https://ci.appveyor.com/api/projects/status/st5pirqiha2pety9?svg=true)](https://ci.appveyor.com/project/PavlyukovVladimir/pavlyukovvvqamid45autotestingselenide)

***Павлюков Владимир Владимирович, группа*** **QAMID45**

# Домашнее задание к занятию «2.2. Selenide»

<details><summary>Вводная часть.</summary>

В качестве результата пришлите ссылку на ваш GitHub-проект в личном кабинете студента на сайте [netology.ru](https://netology.ru).

Все задачи этого занятия нужно делать в одном репозитории.

**Важно**: если у вас что-то не получилось, то оформляйте issue [по установленным правилам](https://github.com/netology-code/aqa-homeworks/blob/master/report-requirements.md).

**Важно**: не делайте ДЗ всех занятий в одном репозитории. Иначе вам потом придётся достаточно сложно подключать системы Continuous integration.

## Как сдавать задачи

1. Инициализируйте на своём компьютере пустой Git-репозиторий.
1. Добавьте в него готовый файл [.gitignore](https://github.com/netology-code/aqa-homeworks/blob/master/.gitignore).
1. Добавьте в этот же каталог код ваших автотестов.
1. Сделайте необходимые коммиты.
1. Добавьте в каталог `artifacts` целевой сервис (`app-card-delivery.jar` — см. раздел «Настройка CI»).
1. Создайте публичный репозиторий на GitHub и свяжите свой локальный репозиторий с удалённым.
1. Сделайте пуш — удостоверьтесь, что ваш код появился на GitHub.
1. Удостоверьтесь, что на AppVeyor сборка зелёная.
1. Поставьте бейджик сборки вашего проекта в файл README.md.
1. Ссылку на ваш проект отправьте в личном кабинете на сайте [netology.ru](https://netology.ru).
1. Задачи, отмеченные как необязательные, можно не сдавать, это не повлияет на получение зачёта.

## Настройка CI

Настройка CI осуществляется аналогично предыдущему заданию, за исключением того, что файл целевого сервиса теперь называется `app-card-delivery.jar`.

</details>

## Задача №1: заказ доставки карты

<details><summary>Развернуть Задача №1: заказ доставки карты</summary>

Вам необходимо автоматизировать тестирование формы заказа доставки карты:

![](https://github.com/netology-code/aqa-homeworks/raw/master/selenide/pic/order.png)

Требования к содержимому полей:
1. Город — [один из административных центров субъектов РФ](https://ru.wikipedia.org/wiki/%D0%90%D0%B4%D0%BC%D0%B8%D0%BD%D0%B8%D1%81%D1%82%D1%80%D0%B0%D1%82%D0%B8%D0%B2%D0%BD%D1%8B%D0%B5_%D1%86%D0%B5%D0%BD%D1%82%D1%80%D1%8B_%D1%81%D1%83%D0%B1%D1%8A%D0%B5%D0%BA%D1%82%D0%BE%D0%B2_%D0%A0%D0%BE%D1%81%D1%81%D0%B8%D0%B9%D1%81%D0%BA%D0%BE%D0%B9_%D0%A4%D0%B5%D0%B4%D0%B5%D1%80%D0%B0%D1%86%D0%B8%D0%B8).
1. Дата — не ранее трёх дней с текущей даты.
1. В поле фамилии и имени разрешены только русские буквы, дефисы и пробелы.
1. В поле телефона — только 11 цифр, символ + на первом месте.
1. Флажок согласия должен быть выставлен.

Тестируемая функциональность: отправка формы.

Поля «Город» и «Дата» заполняются через прямой ввод значений без использования выбора из выпадающего списка и всплывающего календаря.

Условия: если все поля заполнены корректно, то форма переходит в состояние загрузки:

![](https://github.com/netology-code/aqa-homeworks/raw/master/selenide/pic/loading.png)

Важно: состояние загрузки не должно длиться более 15 секунд.

После успешной отправки формы появится всплывающее окно об успешном завершении бронирования:

![](https://github.com/netology-code/aqa-homeworks/raw/master/selenide/pic/popup.png)

Вам необходимо самостоятельно изучить элементы на странице, чтобы подобрать правильные селекторы. Обратите внимание, что элементы могут быть как скрыты, так и динамически добавляться или удаляться из DOM.

<details>
    <summary>Подсказка</summary>

    Смотрите на `data-test-id`, но помните, что он может быть не у всех элементов.
</details>

<details>
    <summary>Ловушка 😈</summary>

    Дата и время всегда будут уязвимым местом ваших тестов. Ключевая ловушка в том, что, если вы их захардкодите, то тест, который работал сегодня, уже может не работать завтра, через неделю, месяц, потому что дата может перейти в разряд условного прошлого для приложения и стать невалидной.

    Кроме того, дата и время — это одно из немногих мест в тестах, где вам **иногда** придётся писать логику.
</details>

</details>

## Задача №2: взаимодействие с комплексными элементами (необязательная)

<details><summary>Развернуть Задача №2: взаимодействие с комплексными элементами (необязательная)</summary>

Большинство систем старается помогать пользователям ускорить выполнение операций: для этого предоставляются формы с автодополнением и элементы вроде календарей.

Проверьте отправку формы, используя следующие условия:
1. Ввод двух букв в поле «Город», после чего выбор нужного города из выпадающего списка:

![](https://github.com/netology-code/aqa-homeworks/raw/master/selenide/pic/dropdown.png)

2. Выбор даты на неделю вперёд, начиная от текущей даты, через инструмент календаря:

![](https://github.com/netology-code/aqa-homeworks/raw/master/selenide/pic/calendar.png)

**Важно: предлагаемая вам задача действительно сложная и потребует от вас достаточно много усилий для решения. Именно поэтому мы перенесли её в разряд необязательных.**

P.S. Стоит отметить, что перед автоматизацией вы должны попробовать оценить стоимость автоматизации, в неё же входит и сложность. Но оценивать вы не научитесь, не попробовав автоматизировать.


</details>

# Запуск тестов

* Runs server: `java -jar artifacts/app-card-delivery.jar`
* Runs all tests: `./gradlew clean test`
* [Просмотр отчета(локальное выполнение тестов)](build/reports/tests/test/index.html)
* [Просмотр отчета(appveyor выполнение тестов при push. Можно скачать архив отчета reports.zip, расположен на вкладке Artifacts)](https://ci.appveyor.com/project/PavlyukovVladimir/pavlyukovvvqamid45autotestingwebi/history)

# Багрепорты

* [Ошибка в сообщении об ошибке, когда поле имя неверно](https://github.com/PavlyukovVladimir/PavlyukovVVQamid45AutotestingSelenide/issues/7)
* [Админ центры Гатчина и Красногорск вызывают ошибку](https://github.com/PavlyukovVladimir/PavlyukovVVQamid45AutotestingSelenide/issues/6)
* [Ё в имени вызывает ошибку](https://github.com/PavlyukovVladimir/PavlyukovVVQamid45AutotestingSelenide/issues/2)
* [ё в имени вызывает ошибку](https://github.com/PavlyukovVladimir/PavlyukovVVQamid45AutotestingSelenide/issues/1)
* [Имя "-" не вызывает ошибку](https://github.com/PavlyukovVladimir/PavlyukovVVQamid45AutotestingSelenide/issues/5)
* [Имя с "-" в конце не вызывает ошибку](https://github.com/PavlyukovVladimir/PavlyukovVVQamid45AutotestingSelenide/issues/4)
* [Имя с "-" в начале не вызывает ошибку](https://github.com/PavlyukovVladimir/PavlyukovVVQamid45AutotestingSelenide/issues/3)
