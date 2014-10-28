#!/bin/bash
cd ${0%/*}
java -classpath "./target/MAL_DEMO-1.0-jar-with-dependencies.jar" esa.mo.mal.demo.provider.DemoProviderCli
