$FileName = "outages-0.0.1-SNAPSHOT.jar"

if (Test-Path $FileName) {
  Remove-Item $FileName
  write-host "$FileName has been deleted"
}
else {
  Write-host "$FileName doesn't exist"
}

Copy-Item 'target/outages-0.0.1-SNAPSHOT.jar' 'src/main/docker/outages-0.0.1-SNAPSHOT.jar'

Set-Location -Path src/main/docker

docker build --tag=outages-api:v1.0 .