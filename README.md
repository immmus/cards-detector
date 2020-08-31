# Варианты запуска программы

1) Способ первый:
 собрать jar файл: ```mvn clean intall```
 файл будет находить в target/cards-detector-jar-with-dependencies.jar
 запусть из консоли: ```java -jar (путь к jar файлу)```
 
2) Способ второй:
в архиве уже лежит собраный cards-detector-jar-with-dependencies.jar
В unix запуск с помощью run.sh
В windows достаточно два раза кликнуть на run.bat и откроется консоль.

# Как использовать
Когда программа запустится, попросит передать путь
``enter 'detect + path' or stop for terminate. For additional info enter 'einfo'``
Например : 
1) для unix
```
detect /opt/imgs/20180821_055341.782_0x26080126.png
```

2) для  windows
```
detect D:\imgs\20180821_055341.782_0x26080126.png
```

Либо путь к папке со всеми изображениями:              

```detect /opt/imgs``` - для unix
```detect D:\imgs```   - для  windows

Программа будет работать до тех пор пока за место пути не передать "stop", либо просто закрыть.

ВАЖНО!!! для запуска программы должна быть установленна java 11.
Доп. вопросы можно задать по почте fonto.trg@gmail.com
