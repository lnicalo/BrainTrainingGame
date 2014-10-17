odbcconf.exe /a {CONFIGDSN "Microsoft Access Driver (*.mdb)" "DSN=JDBC_BRAINTRAINING|Description=Origen de datos de braintraining|DBQ=%cd%\braintraining.mdb"}
echo OFF
echo "Se ha configurado el origen de datos de 'braintraining' correctamente"
pause